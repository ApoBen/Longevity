package com.shealt.healthreport.data.repository

import android.app.Activity
import android.util.Log
import com.samsung.android.sdk.health.data.HealthDataStore
import com.samsung.android.sdk.health.data.error.ResolvablePlatformException
import com.samsung.android.sdk.health.data.permission.AccessType
import com.samsung.android.sdk.health.data.permission.Permission
import com.samsung.android.sdk.health.data.request.DataTypes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthPermissionManager @Inject constructor(
    private val healthDataStore: HealthDataStore
) {
    companion object {
        private const val TAG = "HealthPermissionManager"
    }

    private val requiredPermissions = setOf(
        Permission.of(DataTypes.SLEEP, AccessType.READ),
        Permission.of(DataTypes.STEPS, AccessType.READ),
        Permission.of(DataTypes.HEART_RATE, AccessType.READ),
        Permission.of(DataTypes.EXERCISE, AccessType.READ),
        Permission.of(DataTypes.STEPS_GOAL, AccessType.READ),
        Permission.of(DataTypes.ENERGY_SCORE, AccessType.READ),
        Permission.of(DataTypes.ACTIVITY_SUMMARY, AccessType.READ),
        Permission.of(DataTypes.BLOOD_PRESSURE, AccessType.READ),
        Permission.of(DataTypes.BLOOD_OXYGEN, AccessType.READ),
        Permission.of(DataTypes.BLOOD_GLUCOSE, AccessType.READ),
        Permission.of(DataTypes.BODY_COMPOSITION, AccessType.READ),
        Permission.of(DataTypes.NUTRITION, AccessType.READ),
        Permission.of(DataTypes.WATER_INTAKE, AccessType.READ),
        Permission.of(DataTypes.WATER_INTAKE_GOAL, AccessType.READ),
        Permission.of(DataTypes.FLOORS_CLIMBED, AccessType.READ),
        Permission.of(DataTypes.SKIN_TEMPERATURE, AccessType.READ),
        Permission.of(DataTypes.SLEEP_APNEA, AccessType.READ),
        Permission.of(DataTypes.USER_PROFILE, AccessType.READ)
    )

    suspend fun hasAllPermissions(): Boolean {
        return try {
            Log.d(TAG, "Checking permissions for: ${requiredPermissions.size} types")
            val granted = healthDataStore.getGrantedPermissions(requiredPermissions)
            Log.d(TAG, "Granted permissions: ${granted.size} / ${requiredPermissions.size}")
            granted.containsAll(requiredPermissions)
        } catch (e: Exception) {
            Log.e(TAG, "hasAllPermissions failed: ${e.javaClass.simpleName} - ${e.message}", e)
            false
        }
    }

    suspend fun requestPermissions(activity: Activity): Boolean {
        Log.d(TAG, "requestPermissions called with activity: ${activity.javaClass.simpleName}")
        return try {
            val granted = healthDataStore.requestPermissions(requiredPermissions, activity)
            Log.d(TAG, "Permissions granted: ${granted.size} / ${requiredPermissions.size}")
            granted.forEach { p ->
                Log.d(TAG, "  Granted: $p")
            }
            granted.containsAll(requiredPermissions)
        } catch (e: ResolvablePlatformException) {
            Log.w(TAG, "ResolvablePlatformException caught: hasResolution=${e.hasResolution}", e)
            if (e.hasResolution) {
                Log.d(TAG, "Resolving platform exception using activity")
                e.resolve(activity)
            }
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "requestPermissions FAILED: ${e.javaClass.simpleName} - ${e.message}", e)
            throw e  // Re-throw so the ViewModel can show the error
        }
    }
}
