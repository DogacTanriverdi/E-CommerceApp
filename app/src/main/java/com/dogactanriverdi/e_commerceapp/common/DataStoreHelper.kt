package com.dogactanriverdi.e_commerceapp.common

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dogactanriverdi.e_commerceapp.common.Constants.DATASTORE_NAME
import kotlinx.coroutines.flow.firstOrNull

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

suspend fun saveUserId(context: Context, key: String, value: String) {
    val dataStoreKey = stringPreferencesKey(key)
    context.dataStore.edit { preferences ->
        preferences[dataStoreKey] = value
    }
}

suspend fun readUserId(context: Context, key: String): String? {
    val dataStoreKey = stringPreferencesKey(key)
    val preferences = context.dataStore.data.firstOrNull()
    return preferences?.get(dataStoreKey)
}