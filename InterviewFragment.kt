package com.rbinternational.retail.mobileapp.mynewton.ui.interview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.databinding.ViewDataBinding
import com.rbinternational.retail.mobileapp.data.cards.model.Card
import com.rbinternational.retail.mobileapp.data.cards.model.CardDetails
import com.rbinternational.retail.mobileapp.data.cards.model.CardLimit
import com.rbinternational.retail.mobileapp.data.cards.model.CardSummary
import com.rbinternational.retail.mobileapp.data.cards.model.CardType
import com.rbinternational.retail.mobileapp.mynewton.BR
import com.rbinternational.retail.mobileapp.mynewton.R
import com.rbinternational.retail.mobileapp.mynewton.databinding.InterviewActivityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class InterviewActivity : AppCompatActivity() {

    private val viewModel: InterviewViewModel by viewModel()
    private lateinit var binding: InterviewActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = InterviewActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cardsView.setContent {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                viewModel.cards.collectAsState().value.forEach { c ->
                    CardView(padding = 8.dp, card = c)
                }
            }
        }

        (binding as ViewDataBinding).run {
            lifecycleOwner = this@InterviewActivity
            setVariable(BR.vm, viewModel)
            executePendingBindings()
        }

        viewModel.getAccountData(id = "ID", context = this)
    }

    @Composable
    fun CardView(
        padding: Dp,
        card: Card
    ) {
        Row(
            modifier = Modifier.padding(padding)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_card_bank_raiffeisen),
                contentDescription = ""
            )
            Column(modifier = Modifier.padding(start = padding)) {
                Text(text = card.details.businessName)
                Text(text = card.summary.number)
            }
        }
    }

    @Preview
    @Composable
    fun CardViewPreview() {
        CardView(
            padding = 6.dp,
            card = Card(
                actions = null,
                details = CardDetails(
                    accountId = "ACCOUNT_ID",
                    linkedAccountId = null,
                    businessName = "BUSINESS_NAME",
                    currency = "HUF",
                    embossNameCompany = "EMBOSSED_COMPANY_NAME",
                    expiryDate = "EXPIRY_DATE",
                    mainCard = true
                ),
                id = "CARD_ID",
                limit = CardLimit(),
                status = Card.Status.ACTIVE,
                summary = CardSummary(
                    embossedName = "EMBOSSED_NAME",
                    number = "NUMBER",
                    type = CardType.CURRENT,
                    vendor = "VENDOR"
                ),
                lastSelected = true
            )
        )
    }
}
