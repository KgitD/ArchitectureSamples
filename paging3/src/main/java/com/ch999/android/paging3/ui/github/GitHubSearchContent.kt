package com.ch999.android.paging3.ui.github

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ch999.android.base.ui.theme.White245
import com.ch999.android.paging3.R
import com.ch999.android.paging3.model.RepositoryItem
import com.ch999.android.paging3.ui.common.FullScreenLoading
import com.ch999.android.paging3.ui.common.LoadingContent
import com.ch999.android.paging3.ui.style.contentTextStyle
import com.ch999.android.paging3.ui.style.titleTextStyle
import com.ch999.android.paging3.ui.theme.GitHubTopAppBarColor
import com.ch999.android.paging3.vm.GithubSearchViewModel
import java.net.SocketTimeoutException

private val defaultSpacerSize = 16.dp

@Composable
fun GitHubSearchScreen(vm: GithubSearchViewModel, onBack: () -> Unit) {
    val lazyPagingItems = vm.searchRepositories().collectAsLazyPagingItems()
    Scaffold(topBar = {
        TopAppBar(backgroundColor = GitHubTopAppBarColor, content = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.cd_navigate_up),
                    tint = Color.White
                )
            }
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = "Github Repositories",
                style = MaterialTheme.typography.subtitle2,
                color = Color.White // LocalContentColor.current
            )
        })
    }, content = {
        LoadingContent(
            empty = lazyPagingItems.itemCount == 0,
            emptyContent = { FullScreenLoading() },
            loading = lazyPagingItems.loadState.refresh is LoadState.Loading || lazyPagingItems.loadState.append is
                    LoadState.Loading,
            onRefresh = { lazyPagingItems.refresh() },
            content = {
                RepositoriesScreen(lazyPagingItems = lazyPagingItems)
            }
        )
    })
}

@Composable
private fun RepositoriesScreen(lazyPagingItems: LazyPagingItems<RepositoryItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(White245)
        ) {
            items(lazyPagingItems = lazyPagingItems) { RepositoryContent(repository = it) }
            item { FooterLoadMore(loadState = lazyPagingItems.loadState.append, onRetry = { lazyPagingItems.retry() }) }
        }
    }
}

@Composable
private fun RepositoryContent(repository: RepositoryItem?) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 15.dp, vertical = 15.dp)
    ) {
        val (name, description, starsCount) = createRefs()
        Text(
            text = repository?.name ?: "",
            modifier = Modifier.constrainAs(name) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = description.top)
            }, style = titleTextStyle(), textAlign = TextAlign.Start, maxLines = 2, overflow = TextOverflow.Ellipsis
        )
        Text(
            text = repository?.description ?: "",
            modifier = Modifier.constrainAs(description) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = name.bottom, bottom = starsCount.top)
            }, style = contentTextStyle(), textAlign = TextAlign.Start
        )
        Text(
            text = "stars: ${repository?.stargazersCount ?: 0}",
            modifier = Modifier.constrainAs(starsCount) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = description.bottom, bottom = parent.bottom)
            }, style = contentTextStyle(), textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
private fun PreviewRepositoryContent() = RepositoryContent(
    repository = RepositoryItem(
        name = "flutter",
        description = "Flutter makes it easy and fast to build beautiful apps for mobile and beyond.",
        stargazersCount = 117388
    )
)

@Composable
fun EmptyView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = com.ch999.android.base.R.mipmap.icon_data_empty), contentDescription = "")
        Spacer(modifier = Modifier.size(defaultSpacerSize))
        Text(text = "暂无数据")
    }
}

@Preview
@Composable
private fun PreviewEmptyView() = EmptyView()

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White, shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
    }
}

@Preview
@Composable
private fun PreviewLoadingView() = LoadingView()

@Composable
fun FooterLoadMore(loadState: LoadState, onRetry: (() -> Unit) = {}) {
    val message = when {
        loadState.endOfPaginationReached -> "已加载完所有数据"
        loadState is LoadState.Loading -> "正在加载更多数据..."
        loadState is LoadState.NotLoading -> "加载成功"
        loadState is LoadState.Error -> "加载失败：${loadState.error.localizedMessage}\n点击重新加载"
        else -> null
    }
    if (message.isNullOrEmpty()) return
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable(enabled = loadState is LoadState.Error, onClick = onRetry),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            modifier = Modifier.wrapContentWidth(),
            textAlign = TextAlign.Center,
            style = contentTextStyle()
        )
    }
}

@Preview
@Composable
private fun PreviewFooterLoadMore() = FooterLoadMore(loadState = LoadState.Error(SocketTimeoutException("请求超时")))