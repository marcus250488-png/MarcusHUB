package com.b3insights.app.auth

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

/**
 * Login com Google + Firebase Authentication.
 * Guarda nome e foto do perfil localmente para exibir no app.
 */
class AuthManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("b3_auth", Context.MODE_PRIVATE)

    private val firebaseAuth: FirebaseAuth? by lazy {
        runCatching { FirebaseAuth.getInstance() }.getOrNull()
    }

    val currentUser get() = firebaseAuth?.currentUser

    val displayName: String?
        get() = prefs.getString("display_name", null) ?: currentUser?.displayName

    val email: String?
        get() = prefs.getString("email", null) ?: currentUser?.email

    val photoUrl: String?
        get() = prefs.getString("photo_url", null) ?: currentUser?.photoUrl?.toString()

    fun getGoogleSignInClient(): GoogleSignInClient? {
        val webClientId = context.resources.getIdentifier(
            "default_web_client_id", "string", context.packageName
        )
        if (webClientId == 0) return null

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(webClientId))
            .requestEmail()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    fun getSignInIntent(): Intent? = getGoogleSignInClient()?.signInIntent

    suspend fun handleSignInResult(data: Intent?): Result<GoogleSignInAccount> {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val auth = firebaseAuth ?: return Result.failure(IllegalStateException("Firebase não configurado"))
            auth.signInWithCredential(credential).await()
            saveProfile(account)
            Result.success(account)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun saveProfile(account: GoogleSignInAccount) {
        val email = account.email
        val alreadyWelcomed = email != null && prefs.getBoolean("welcomed_$email", false)
        prefs.edit()
            .putString("display_name", account.displayName)
            .putString("email", email)
            .putString("photo_url", account.photoUrl?.toString())
            .putBoolean("pending_welcome_email", !alreadyWelcomed)
            .apply()
    }

    /** True se este login ainda não recebeu e-mail de boas-vindas. */
    fun consumePendingWelcome(): Pair<String, String>? {
        if (!prefs.getBoolean("pending_welcome_email", false)) return null
        val email = prefs.getString("email", null) ?: return null
        val name = prefs.getString("display_name", null) ?: "Investidor"
        prefs.edit()
            .putBoolean("pending_welcome_email", false)
            .putBoolean("welcomed_$email", true)
            .apply()
        return name to email
    }

    fun hasPendingWelcome(): Boolean = prefs.getBoolean("pending_welcome_email", false)


    fun signOut() {
        firebaseAuth?.signOut()
        getGoogleSignInClient()?.signOut()
        prefs.edit().clear().apply()
    }
}
