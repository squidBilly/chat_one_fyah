package com.snowyfox.chatonefiyah.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable


interface Routes {
    @Serializable
    data object StartScreen : Routes

    @Serializable
    data class ChatScreen(val chatId: String) : Routes

    @Serializable
    data object ConversationList : Routes

    @Serializable
    data object Calls : Routes

    @Serializable
    data object NewConversation : Routes
}


@Composable
fun FiyahChatNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.ConversationList) {

    }
}

private fun NavGraphBuilder.addConversationList(navController: NavController) {
    composable<Routes.ConversationList> {

    }

}

private fun NavGraphBuilder.addNewConversation(navController: NavController) {
    composable<Routes.NewConversation> {

    }

}

private fun NavGraphBuilder.addChat(navController: NavController) {
    composable<Routes.ChatScreen>(
        deepLinks = listOf(
            navDeepLink<Routes.ChatScreen>(basePath = "https://whatspack.com/chat?chatId={chatId}")
        )
    ) {
        val chatId = it.toRoute<Routes.ChatScreen>().chatId

    }
}