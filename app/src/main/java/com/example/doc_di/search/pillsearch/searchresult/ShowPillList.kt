package com.example.doc_di.search.pillsearch.searchresult

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.doc_di.UserViewModel
import com.example.doc_di.domain.model.Pill
import com.example.doc_di.etc.Routes
import com.example.doc_di.search.SearchViewModel
import com.example.doc_di.search.pillsearch.searchresult.pill_information.ReviewViewModel

@Composable
fun ShowPillList(
    pillList: List<Pill>,
    navController: NavController,
    userViewModel: UserViewModel,
    searchViewModel: SearchViewModel,
    reviewViewModel: ReviewViewModel,
) {
    val cardPillTextColor = Color(0xFF262C3D)
    val reviewTextColor = Color(0xFF747F9E)
    val starColor = Color(0xFFFFC107)

    val context = LocalContext.current
    LazyColumn {
        items(pillList) { pill ->
            Card(
                shape = MaterialTheme.shapes.medium,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable {
                        reviewViewModel.showSearch[0] = true
                        reviewViewModel.showSearch[1] = false
                        reviewViewModel.showSearch[2] = false
                        reviewViewModel.showSearch[3] = false
                        searchViewModel.setSelectedPill(pill)
                        searchViewModel.setPillInfo(pill.itemSeq.toString())
                        reviewViewModel.fetchReviewInfo(
                            context,
                            navController,
                            userViewModel,
                            pill.itemName
                        )
                        navController.navigate(Routes.pillInformation.route) {
                            popUpTo(Routes.searchMethod.route) {
                                inclusive = false
                            }
                        }
                    }
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = pill.itemName,
                        color = cardPillTextColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Star,
                            tint = if (pill.rateAmount != 0) starColor else reviewTextColor,
                            contentDescription = "별점",
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (pill.rateAmount != 0) {
                                val rate = pill.rateTotal.toDouble() / pill.rateAmount
                                String.format("%.1f", rate) + "(${pill.rateAmount})"
                            } else {
                                "후기 미제공"
                            },
                            color = reviewTextColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}