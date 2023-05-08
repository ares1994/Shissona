package com.arepadeobiri.android.shissona

import android.os.Bundle
import android.view.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.arepadeobiri.android.shissona.viewModels.HomeViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val viewModel:HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                HomeComposable()
            }
        }
    }


    @Composable
    fun HomeComposable() {
        val scaffold = rememberScaffoldState()
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFf8f8f8))
                .padding(bottom = 80.dp, top = 16.dp, start = 16.dp, end = 16.dp)
            ,
            scaffoldState = scaffold
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFf8f8f8))
                    .padding(it)
            ) {
                val amount by viewModel.amount.collectAsState()
                val selectedIndex by viewModel.selectedIndex.collectAsState()
                val details by viewModel.details.collectAsState()
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFf8f8f8))
                        .verticalScroll(rememberScrollState())
                ) {


                    CustomTextField(value = amount, onChange = {
                        viewModel.setAmount(it.filter { it.isDigit() })
                    }, label = "Enter Expense Amount", keyboardType = KeyboardType.Number)


                    Spacer(modifier = Modifier.height(30.dp))

                    LazyVerticalGrid(userScrollEnabled = false, modifier = Modifier
                        .background(
                            colorResource(id = android.R.color.white),
                            RoundedCornerShape(15.dp)
                        )
                        .padding(vertical = 16.dp)
                        .heightIn(0.dp, 300.dp), columns = GridCells.Fixed(4), content = {
                        itemsIndexed(Util.ITEMS) { index, item ->

                            val modifier = Modifier.border(
                                BorderStroke(
                                    1.dp,
                                    colorResource(id = R.color.colorPrimary)
                                ),
                                RoundedCornerShape(5.dp)
                            )

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.setSelectedIndex(index)
                                    }
                                    .then(if (selectedIndex == index) modifier else Modifier),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Image(
                                    painter = painterResource(id = item.imageResource),
                                    contentDescription = item.name,
                                    colorFilter = ColorFilter.tint(colorResource(id = item.colorTint))
                                )
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(
                                        top = 8.dp,
                                        start = 4.dp,
                                        end = 4.dp
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                            }
                        }
                    })




                    Spacer(modifier = Modifier.height(30.dp))

                    CustomTextField(value = details, onChange = { value ->
                        viewModel.setDetails(value)
                    }, label = "Details", keyboardType = KeyboardType.Text)


                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth(), verticalArrangement = Arrangement.Bottom
                ) {
                    Button(
                        onClick = {
                            if (details.isBlank()){
                                lifecycleScope.launch {
                                    scaffold.snackbarHostState.showSnackbar("Please provide a description")
                                }
                                return@Button
                            }
                            if (amount.isBlank()){
                                lifecycleScope.launch {
                                    scaffold.snackbarHostState.showSnackbar("Please provide an amount")
                                }
                                return@Button
                            }

                            if(selectedIndex == -1){
                                lifecycleScope.launch {
                                    scaffold.snackbarHostState.showSnackbar("Please select a category")
                                }
                                return@Button
                            }


                            viewModel.insert()
                            lifecycleScope.launch {
                                scaffold.snackbarHostState.showSnackbar("Data Saved")
                            }
                        }, modifier = Modifier
                            .fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.colorPrimary),
                            contentColor = colorResource(id = android.R.color.white)
                        ), contentPadding = PaddingValues(16.dp)
                    ) {
                        Text(text = "Add")
                    }

                }

            }


        }


    }

    @Composable
    fun CustomTextField(
        value: String,
        onChange: (value: String) -> Unit,
        label: String,
        keyboardType: KeyboardType
    ) {
        TextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier
                .fillMaxWidth(),
            label = {
                Text(text = label)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = colorResource(id = android.R.color.black),
                focusedLabelColor = colorResource(id = android.R.color.black),
                backgroundColor = colorResource(id = android.R.color.transparent)
            ),
        )
    }

}
