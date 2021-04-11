package com.ch999.android.ui.main

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.alibaba.android.arouter.launcher.ARouter
import com.ch999.android.base.ui.theme.White245
import com.ch999.android.model.RouterTableItem
import com.ch999.android.model.SettingItem
import com.ch999.android.model.UserInfo
import com.ch999.android.paging3.ui.style.contentTextStyle
import com.ch999.android.paging3.ui.style.titleTextStyle
import com.ch999.android.ui.theme.TealGreen
import com.ch999.android.vm.MainViewModel
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.glide.GlideImage
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import dev.chrisbanes.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@Composable
fun MainContent(vm: MainViewModel) {
    HomeScreen(vm = vm)
}

@Composable
private fun RouterTablesScreen(tableItems: List<RouterTableItem>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(White245),
        reverseLayout = false
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
            text = item?.description ?: "",
            modifier = Modifier.constrainAs(path) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = description.top)
            }, style = titleTextStyle(), textAlign = TextAlign.Start, maxLines = 2, overflow = TextOverflow.Ellipsis
        )
        Text(
            text = item?.path ?: "",
            modifier = Modifier.constrainAs(description) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = path.bottom, bottom = parent.bottom)
            }, style = contentTextStyle(), textAlign = TextAlign.Start, maxLines = 2, overflow = TextOverflow.Ellipsis
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun HomeScreen(vm: MainViewModel) {
    // val insets = LocalWindowInsets.current
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val userInfoState: State<UserInfo?> = vm.currentAccount().observeAsState()
    val settingItemsState: State<MutableList<SettingItem>?> = vm.homeDrawerItems().observeAsState()
    Scaffold(scaffoldState = scaffoldState, drawerContent = {
        HomeDrawer(
            userInfoState = userInfoState,
            settingItemsState = settingItemsState
        )
    }, topBar = {
        val toolbarTitle = "Compose Training"
        TopAppBar(
            // https://google.github.io/accompanist/insets/
            modifier = Modifier.statusBarsPadding(),
            backgroundColor = TealGreen,
            contentColor = Color.White,
            elevation = 3.dp,
            title = { Text(text = toolbarTitle) },
            navigationIcon = {
                IconButton(
                    onClick = { coroutineScope.launch { scaffoldState.drawerState.open() } },
                    modifier = Modifier.padding(horizontal = 0.dp, vertical = 0.dp)
                ) {
                    userInfoState.value?.avatarImageUrl?.let {
                        GlideImage(
                            data = it,
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                            loading = {
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            })
    }, content = {
        RouterTablesScreen(tableItems = vm.routerTableItems().value ?: ArrayList())
    })
}

@ExperimentalFoundationApi
@Composable
fun HomeDrawer(userInfoState: State<UserInfo?>, settingItemsState: State<MutableList<SettingItem>?>) {
    LazyColumn(
        modifier = Modifier
            .navigationBarsPadding(false)
            .systemBarsPadding(false)
            .fillMaxSize()
            .background(color = White245),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        stickyHeader {
            HomeDrawerHeaderContent(userInfoState = userInfoState)
        }
        settingItemsState.value?.let {
            items(it) { item -> SettingItemContent(item = item) }
        }
    }
}

@Composable
fun HomeDrawerHeaderContent(userInfoState: State<UserInfo?>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        userInfoState.value?.backgroundImageUrl?.let {
            CoilImage(
                data = it,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = { /* TODO do something better here */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(MaterialTheme.shapes.large)
                    .clickable { }
            )
        }
    }
}

@Composable
fun UserInfoContent(userInfoState: State<UserInfo?>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        with(userInfoState.value) {
            Text(
                text = this?.username ?: "",
                style = titleTextStyle(),
                textAlign = TextAlign.Center
            ); Spacer(modifier = Modifier.size(4.dp)); Text(
            text = this?.height ?: "",
            style = contentTextStyle(),
            textAlign = TextAlign.Center
        ); Spacer(modifier = Modifier.size(4.dp)); Text(
            text = this?.weight ?: "",
            style = contentTextStyle(),
            textAlign = TextAlign.Center
        ); Spacer(modifier = Modifier.size(4.dp)); Text(
            text = this?.birthday ?: "",
            style = contentTextStyle(),
            textAlign = TextAlign.Center
        ); Spacer(modifier = Modifier.size(4.dp)); Text(
            text = this?.graduatedSchool ?: "" + " - " + this?.graduatedDatetime,
            style = contentTextStyle(),
            textAlign = TextAlign.Center
        )
        }
    }
}

@Composable
fun SettingItemContent(item: SettingItem?) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .clickable(true, onClick = {})
    ) {
        val (title, subtitle, description) = createRefs()
        Text(
            text = item?.title ?: "",
            modifier = Modifier.constrainAs(title) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = description.top)
            }, style = titleTextStyle(), textAlign = TextAlign.Start, maxLines = 2, overflow = TextOverflow.Ellipsis
        )
        Text(
            text = item?.subtitle ?: "",
            modifier = Modifier.constrainAs(subtitle) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = title.bottom, bottom = description.top)
            }, style = contentTextStyle(), textAlign = TextAlign.Start
        )
        Text(
            text = item?.description ?: "",
            modifier = Modifier.constrainAs(description) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = subtitle.bottom, bottom = parent.bottom)
            }, style = contentTextStyle(), textAlign = TextAlign.Start
        )
    }
}