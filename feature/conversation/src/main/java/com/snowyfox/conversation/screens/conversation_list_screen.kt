package com.snowyfox.conversation.screens

import android.R.attr.text
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.snowyfox.conversation.model.generateTabs
import com.snowyfox.conversation.utils.generateFakeConversations
import com.snowyfox.framework.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConversationListScreen(
    onNewConversationClick: () -> Unit,
    onConversationClick: (chatId: String) -> Unit
) {
    val tabs = generateTabs()
    val pageCount = tabs.size
    val pagerState = rememberPagerState(initialPage = 1) { pageCount }
    var selectedIndex by remember { mutableIntStateOf(1) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.conversations_tab_calls_title)) },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Rounded.Menu, contentDescription = "Hamburger Menu")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.padding(40.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                tonalElevation = 2.dp
            ) {
                SecondaryTabRow(
                    selectedTabIndex = selectedIndex,
                    containerColor = androidx.compose.ui.graphics.Color.Transparent
                ) {
                    tabs.forEachIndexed { index, _ ->
                        Tab(
                            text = { Text(text = stringResource(tabs[index].title)) },
                            selected = index == selectedIndex,
                            onClick = {
                                selectedIndex = index
                            }
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.colorScheme.primary,
                onClick = { onNewConversationClick() }
            ) {
                Icon(
                    Icons.Rounded.Add,
                    contentDescription = "Add Button"
                )
            }
        },
    ) { paddingValues ->
        HorizontalPager(
            modifier = Modifier.padding(paddingValues),
            state = pagerState,
        ) { index ->
            when (index) {
                0 -> {
                    //Staus
                }

                1 -> {
                    ConversationLazyList(
                        conversations = generateFakeConversations(),
                        onConversationClicked = { onConversationClick(it) }
                    )
                }

                2 -> {
                    //Calls
                }
            }
        }
        LaunchedEffect(selectedIndex) {
            pagerState.animateScrollToPage(selectedIndex)
        }
    }
}

