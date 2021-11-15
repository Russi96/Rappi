package com.example.rappitv.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.rappitv.utils.Constants.DEFAULT_CATEGORIES
import com.example.rappitv.utils.Constants.MOVIE_PARAM
import com.example.rappitv.utils.Constants.PREFERENCES_BACK_ONLINE
import com.example.rappitv.utils.Constants.PREFERENCES_CATEGORIES
import com.example.rappitv.utils.Constants.PREFERENCES_CATEGORIES_ID
import com.example.rappitv.utils.Constants.PREFERENCES_NAME
import com.example.rappitv.utils.Constants.PREFERENCES_WATCH
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    companion object PreferencesKey {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            PREFERENCES_NAME
        )
        val selectedCategory = stringPreferencesKey(PREFERENCES_CATEGORIES)
        val selectedCategoryId = intPreferencesKey(PREFERENCES_CATEGORIES_ID)
        val selectedBackOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
        val selectedWatchType = stringPreferencesKey(PREFERENCES_WATCH)
    }

    private val dataStoreIntroduction = context.dataStore


    suspend fun saveCategories(category: String, categoryId: Int) {
        dataStoreIntroduction.edit { preference ->
            preference[selectedCategory] = category
            preference[selectedCategoryId] = categoryId
        }
    }


    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStoreIntroduction.edit { preferences ->
            preferences[selectedBackOnline] = backOnline
        }
    }

    suspend fun saveWatchType(watch: String){
        dataStoreIntroduction.edit { preferences ->
            preferences[selectedWatchType] = watch
        }
    }


    val readCategoriesType: Flow<CategoriesType> = dataStoreIntroduction.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedCategory = preferences[selectedCategory] ?: DEFAULT_CATEGORIES
            val selectedCategoryId = preferences[selectedCategoryId] ?: 0

            CategoriesType(
                selectedCategory,
                selectedCategoryId,
            )

        }

    val readBackOnline: Flow<Boolean> = dataStoreIntroduction.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[selectedBackOnline] ?: false
            backOnline
        }

    val readWatch: Flow<String> = dataStoreIntroduction.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val watch = preferences[selectedWatchType] ?: MOVIE_PARAM
            watch
        }
}

data class CategoriesType(
    val selectedCategory: String,
    val selectedCategoryId: Int,
)