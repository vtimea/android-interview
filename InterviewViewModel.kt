package com.rbinternational.retail.mobileapp.mynewton.ui.interview

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbinternational.retail.mobileapp.common.locale.LocaleProvider
import com.rbinternational.retail.mobileapp.data.accounts.data.AccountsRepository
import com.rbinternational.retail.mobileapp.data.cards.data.CardsRepository
import com.rbinternational.retail.mobileapp.data.cards.model.Card
import com.rbinternational.retail.mobileapp.mynewton.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.await

class InterviewViewModel(
    val accountsRepository: AccountsRepository,
    val cardRepository: CardsRepository,
    localeProvider: LocaleProvider
) : ViewModel() {

    val accountNumber = MutableLiveData<String>()
    val accountNum = MutableStateFlow("")

    val accountHolderName = MutableLiveData<String>()

    val egyenleg = MutableStateFlow<String?>(null)

    val cards = MutableStateFlow<List<Card>>(emptyList())

    fun getAccountData(id: String, context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            val accounts = accountsRepository.getAccounts().await()
            val currentAccount = accounts[id]

            accountNumber.postValue(context.getString(R.string.account_number_title))
            accountNum.value = currentAccount?.iban.orEmpty()
            egyenleg.value = currentAccount?.balance?.toString()
            accountHolderName.value = currentAccount?.accountOwnerName.toString()

            val cards = cardRepository.getCards().await()
            this@InterviewViewModel.cards.value = cards
        }
    }
}
