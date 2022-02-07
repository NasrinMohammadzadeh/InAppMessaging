package com.example.inappmessaging.di


import com.example.inappmessaging.inappmessaging.InAppMessageHandler
import com.example.inappmessaging.util.AppUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

  @Provides
  @Singleton
  internal fun providesAppUtil(): AppUtil {
    return AppUtil()
  }

  @Provides
  @Singleton
  internal fun providesInAppMessageHandler(appUtil: AppUtil): InAppMessageHandler {
    return InAppMessageHandler(appUtil)
  }


}
