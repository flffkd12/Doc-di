package com.example.doc_di.etc

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.doc_di.UserViewModel
import com.example.doc_di.chatbot.ChatListScreen
import com.example.doc_di.domain.RetrofitInstance
import com.example.doc_di.domain.pill.PillsSearchRepositoryImpl
import com.example.doc_di.home.Home
import com.example.doc_di.home.account_manage.ModifyLogoutAccountDelete
import com.example.doc_di.home.account_manage.modify_profile.Profile
import com.example.doc_di.home.appointment_schedule.AppointmentSchedule
import com.example.doc_di.login.ResetPage
import com.example.doc_di.login.loginpage.LoginPage
import com.example.doc_di.login.register.RegisterPage
import com.example.doc_di.management.addmedication.AddMedicationScreenUI
import com.example.doc_di.management.addschedule.AddScheduleScreenUI
import com.example.doc_di.management.home.ManagementScreen
import com.example.doc_di.search.Search
import com.example.doc_di.search.SearchViewModel
import com.example.doc_di.search.pillsearch.searchmethod.SearchMethod
import com.example.doc_di.search.pillsearch.searchresult.SearchResult
import com.example.doc_di.search.pillsearch.searchresult.pill_information.PillInformation
import com.example.doc_di.search.pillsearch.searchresult.pill_information.ReviewViewModel
import com.example.doc_di.search.trash_maybe.MedicalAppointmentRecord
import com.example.doc_di.search.trash_maybe.PrescribedMedicineList
import com.example.doc_di.search.trash_maybe.PrescriptionRecord

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun NaviGraph(navController: NavHostController) {
    val searchViewModel: SearchViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(PillsSearchRepositoryImpl(RetrofitInstance.pillApi)) as T
        }
    })

    val btmBarViewModel: BtmBarViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val reviewViewModel: ReviewViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.login.route) {

        composable(Routes.login.route) {
            LoginPage(navController, userViewModel)
        }

        composable(Routes.register.route) {
            RegisterPage(navController)
        }

        composable(Routes.reset.route) {
            ResetPage(navController)
        }

        composable(route = Routes.home.route) {
            Home(navController, btmBarViewModel, userViewModel)
        }

        composable(route = Routes.appointmentSchedule.route) {
            AppointmentSchedule(navController, btmBarViewModel)
        }

        composable(
            route = Routes.modifyLogoutAccountDelete.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(700)
                )
            }
        ) {
            ModifyLogoutAccountDelete(navController, userViewModel)
        }

        composable(route = Routes.profile.route) {
            Profile(navController, userViewModel)
        }

        composable(route = Routes.search.route) {
            Search(navController, searchViewModel, btmBarViewModel, userViewModel)
        }

        composable(route = Routes.searchMethod.route) {
            SearchMethod(navController, searchViewModel, btmBarViewModel)
        }

        composable(route = Routes.searchResult.route) {
            SearchResult(
                navController,
                btmBarViewModel,
                userViewModel,
                searchViewModel,
                reviewViewModel
            )
        }

        composable(route = Routes.pillInformation.route) {
            PillInformation(
                navController,
                btmBarViewModel,
                searchViewModel,
                userViewModel,
                reviewViewModel
            )
        }

        composable(route = Routes.medicalAppointmentRecord.route) {
            MedicalAppointmentRecord(navController, btmBarViewModel)
        }

        composable(route = Routes.prescriptionRecord.route) {
            PrescriptionRecord(navController, btmBarViewModel)
        }

        composable(route = Routes.prescribedMedicineList.route) {
            PrescribedMedicineList(navController, btmBarViewModel)
        }

        composable(route = Routes.chatListScreen.route) {
            ChatListScreen(navController, btmBarViewModel)
        }

        composable(route = Routes.managementScreen.route) {
            ManagementScreen(navController, btmBarViewModel)
        }

        composable(route = Routes.addMedicationScreen.route) {
            AddMedicationScreenUI(navController, btmBarViewModel)
        }

        composable(route = Routes.addScheduleScreen.route) {
            AddScheduleScreenUI(navController, btmBarViewModel)
        }
    }
}
