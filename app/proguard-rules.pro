# Base anti-mod / anti-tamper placeholder rules
-keep class androidx.room.RoomDatabase { *; }
-keep class androidx.room.RoomDatabase_Impl { *; }
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**
-dontwarn androidx.room.**

# Keep AdMob entry points
-keep class com.google.android.gms.ads.** { *; }
-dontwarn com.google.android.gms.ads.**

# Preserve Compose tooling metadata used by release shrinking
-keep class kotlin.Metadata { *; }
