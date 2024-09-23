package com.example.doc_di.reminder.home.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.doc_di.R
import com.example.doc_di.domain.model.Reminder
import com.example.doc_di.etc.Routes
import com.example.doc_di.search.SearchViewModel
import com.example.doc_di.search.pillsearch.searchresult.ShowPillList
import com.example.doc_di.search.pillsearch.searchresult.pill_information.ReviewViewModel
import com.example.doc_di.ui.theme.LightBlue

@Composable
fun MedicationCard(
    reminder: Reminder,
    navigateToMedicationDetail: (Reminder) -> Unit,
    deleteReminder: (Int) -> Unit,
    navController: NavController,
    searchViewModel: SearchViewModel,
    reviewViewModel: ReviewViewModel
) {
    var nameSearch by rememberSaveable { mutableStateOf("") }
    val option = mutableMapOf<String, String>()

    val pillList = searchViewModel.pills.collectAsState().value
    val isLoading = searchViewModel.isLoading.collectAsState().value

    LaunchedEffect(navController.currentBackStackEntry) {
        searchViewModel.resetPillInfo()
        searchViewModel.resetPills()
    }

    fun doSearch() {
        option["name"] = nameSearch
        searchViewModel.setOptions(option)
        searchViewModel.searchPillsByOptions()
        navController.navigate(Routes.searchResult.route)
    }

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 20.dp),

    ) {
        // medicationTime null 체크 및 포맷팅
        val formattedMedicationTime = reminder.medicationTime?.let {
            it.split(" ")[1] // "yyyy-MM-dd" 부분만 추출
        } ?: "시간 없음" // 기본값 설정


        nameSearch = reminder.medicineName

        Text(
            text = formattedMedicationTime,
            color = Color.Black, // 검정색 설정
            fontWeight = FontWeight.Bold )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .border(
                    border = BorderStroke(width = 0.5.dp, color = Color(0xFFECEDEF)), // Black border with 2.dp width
                    shape = RoundedCornerShape(30.dp) // Same shape as the card
                ),
            onClick = {
                doSearch()
                      },
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            )
        ) {
            if(isLoading){
                CircularProgressIndicator(color = LightBlue, modifier = Modifier.padding(16.dp))
            }else{
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment
                        .CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .align(Alignment.CenterVertically)
                            .weight(1f),
                        painter = painterResource(id = R.drawable.pillemoji),
                        contentDescription = "용법 이미지"
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Column(
                        modifier = Modifier
                            .weight(3f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = reminder.medicineName,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleSmall
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = reminder.dosage.toString() + " 정",
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.bodyMedium
                        )

                    }

                    Text(
                        text = reminder.recurrence,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .weight(1.5f)
                    )

                    // 점 세 개 아이콘 버튼
                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier.align(Alignment.CenterVertically)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More Options"
                        )
                    }

                    // 드롭다운 메뉴
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                navController.navigate("editMedicationScreen/${reminder.id}")
                            }
                        ) {
                            Text("수정")
                        }
                        DropdownMenuItem(
                            onClick = {
                                expanded = false
                                reminder.id?.let { deleteReminder(it.toInt()) }
                            }
                        ) {
                            Text("삭제")
                        }
                    }
                }
            }


        }
    }

}

