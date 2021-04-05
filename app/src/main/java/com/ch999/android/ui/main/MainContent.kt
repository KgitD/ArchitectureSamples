package com.ch999.android.ui.main

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.ch999.android.base.ui.theme.White245
import com.ch999.android.model.RouterTableItem
import com.ch999.android.paging3.ui.style.contentTextStyle
import com.ch999.android.paging3.ui.style.titleTextStyle
import com.ch999.android.vm.MainViewModel

@Composable
fun MainContent(vm: MainViewModel) {
    Scaffold(content = { RouterTablesScreen(tableItems = vm.routerTableItems().value ?: ArrayList()) })
}

@Composable
private fun RouterTablesScreen(tableItems: List<RouterTableItem>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(White245)
    ) {
        items(tableItems, itemContent = { RouterTableContent(item = it) })
    }
}

@Composable
private fun RouterTableContent(item: RouterTableItem?) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .clickable(enabled = true, onClick = {
                ARouter
                    .getInstance()
                    .build(Uri.parse(item?.path))
                    .navigation()
            })
    ) {
        val (path, description) = createRefs()
        Text(
            text = item?.path ?: "",
            modifier = Modifier.constrainAs(path) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = description.top)
            }, style = titleTextStyle(), textAlign = TextAlign.Start, maxLines = 2, overflow = TextOverflow.Ellipsis
        )
        Text(
            text = item?.description ?: "",
            modifier = Modifier.constrainAs(description) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = path.bottom, bottom = parent.bottom)
            }, style = contentTextStyle(), textAlign = TextAlign.Start, maxLines = 2, overflow = TextOverflow.Ellipsis
        )
    }
}