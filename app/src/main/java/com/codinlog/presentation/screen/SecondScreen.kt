package com.codinlog.presentation.screen

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.codinlog.presentation.ApplicationViewModel
import com.codinlog.presentation.ApplicationViewModelFactory
import com.codinlog.presentation.PresentationScreenRoute
import com.codinlog.presentation.databinding.LayoutSecondScreenBinding
import com.codinlog.presentation.core.BaseScreenContainer
import com.codinlog.presentation.screen.core.PresentationKeyboardScreen
import com.codinlog.presentation.screen.core.viewModelStoreOwner

/**
 * @description TODO
 *
 * @author kouqurong / codinlog@foxmail.com
 * @date 2022/11/9
 */

data class SecondScreenData(val text: String)

@SuppressLint("ViewConstructor")
class SecondScreen(context: Context, parent: BaseScreenContainer) :
    PresentationKeyboardScreen(context, parent) {
    lateinit var mBinding: LayoutSecondScreenBinding
    lateinit var mViewModel: ApplicationViewModel

    override fun onCreateView(parent: BaseScreenContainer): View {
        mBinding = LayoutSecondScreenBinding.inflate(layoutInflater, parent, false)

        mViewModel = ViewModelProvider(
            viewModelStoreOwner,
            ApplicationViewModelFactory()
        )[ApplicationViewModel::class.java]

        return mBinding.root
    }

    override fun onViewCreated() {
        super.onViewCreated()
        mBinding.btn.setOnClickListener {
            mViewModel.setPresentationScreenState(PresentationScreenRoute.FirstScreen)
        }

        mBinding.et.setOnTouchListener { _, _ ->
            showKeyboard(mBinding.et)
            true
        }

        showKeyboard(mBinding.et)
    }

}