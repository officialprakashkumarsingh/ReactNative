package com.ahamai.chatapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahamai.chatapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHeader(
    onClearChat: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDropdownMenu by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.aham_ai_title),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFECECF1)
                    )
                    Text(
                        text = stringResource(R.string.subtitle),
                        fontSize = 12.sp,
                        color = Color(0xFF8E8EA0)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { showDropdownMenu = true }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(R.string.menu),
                    tint = Color(0xFFECECF1)
                )
            }
            
            DropdownMenu(
                expanded = showDropdownMenu,
                onDismissRequest = { showDropdownMenu = false },
                modifier = Modifier.background(Color(0xFF343541))
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.new_chat),
                            color = Color(0xFFECECF1)
                        )
                    },
                    onClick = {
                        onClearChat()
                        showDropdownMenu = false
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = Color(0xFF8E8EA0)
                        )
                    }
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF171717),
            titleContentColor = Color(0xFFECECF1)
        ),
        modifier = modifier
    )
}