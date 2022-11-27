// Generated by Dagger (https://dagger.dev).
package com.abhishek.germanPocketDictionary.ui.agreement.utils;

import com.abhishek.germanPocketDictionary.core.utils.SharedPreferenceManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AgreementPreferenceManagerImpl_Factory implements Factory<AgreementPreferenceManagerImpl> {
  private final Provider<SharedPreferenceManager> sharedPreferenceManagerProvider;

  public AgreementPreferenceManagerImpl_Factory(
      Provider<SharedPreferenceManager> sharedPreferenceManagerProvider) {
    this.sharedPreferenceManagerProvider = sharedPreferenceManagerProvider;
  }

  @Override
  public AgreementPreferenceManagerImpl get() {
    return newInstance(sharedPreferenceManagerProvider.get());
  }

  public static AgreementPreferenceManagerImpl_Factory create(
      Provider<SharedPreferenceManager> sharedPreferenceManagerProvider) {
    return new AgreementPreferenceManagerImpl_Factory(sharedPreferenceManagerProvider);
  }

  public static AgreementPreferenceManagerImpl newInstance(
      SharedPreferenceManager sharedPreferenceManager) {
    return new AgreementPreferenceManagerImpl(sharedPreferenceManager);
  }
}
