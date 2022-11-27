// Generated by Dagger (https://dagger.dev).
package com.abhishek.germanPocketDictionary.ui.home.ui;

import com.abhishek.germanPocketDictionary.core.data.WordsRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<WordsRepository> wordsRepositoryProvider;

  public HomeViewModel_Factory(Provider<WordsRepository> wordsRepositoryProvider) {
    this.wordsRepositoryProvider = wordsRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(wordsRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(Provider<WordsRepository> wordsRepositoryProvider) {
    return new HomeViewModel_Factory(wordsRepositoryProvider);
  }

  public static HomeViewModel newInstance(WordsRepository wordsRepository) {
    return new HomeViewModel(wordsRepository);
  }
}
