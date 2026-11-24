package com.carereminder.app.data.remote

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

object BackendService {

    val supabaseInstance: SupabaseClient = createSupabaseClient(
        supabaseUrl = "https://dlvtcvtflqarnzgyxtgc.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImRsdnRjdnRmbHFhcm56Z3l4dGdjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODA0NjM1NDQsImV4cCI6MjA5NjAzOTU0NH0.8lz8axY0kPVlkd0uCyKIsGQ2pwjYOx9c_0sVckhlpvc"
    ) {
        install(Auth.Companion)
        install(Postgrest.Companion)
        install(Storage.Companion)
        install(Realtime.Companion)
    }

    fun currentUserId(): String? {
        return supabaseInstance.auth.currentUserOrNull()?.id
    }
}