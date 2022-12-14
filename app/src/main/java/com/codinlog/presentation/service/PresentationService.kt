package com.codinlog.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import androidx.lifecycle.*
import com.codinlog.presentation.ApplicationViewModel
import com.codinlog.presentation.ApplicationViewModelFactory
import com.codinlog.presentation.IPresentationAidlInterface
import com.codinlog.presentation.PresentationDialogState.ServiceHideState
import com.codinlog.presentation.PresentationDialogState.ServiceShowState
import com.codinlog.presentation.PresentationScreenRoute
import com.codinlog.presentation.core.ApplicationViewModelStoreProvider
import com.codinlog.presentation.dialog.PresentationDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class PresentationService : Service(), LifecycleOwner, ViewModelStoreOwner {

    private val mDispatcher = ServiceLifecycleDispatcher(this)

    private val mBinder = RemoteViewBinder()

    @Inject
    lateinit var presentationDialog: PresentationDialog

    private lateinit var mAppViewModel: ApplicationViewModel

    override fun onCreate() {
        mDispatcher.onServicePreSuperOnCreate()
        super.onCreate()

        mAppViewModel =
            ViewModelProvider(this, ApplicationViewModelFactory())[ApplicationViewModel::class.java]

        lifecycleScope.launchWhenCreated {
            mAppViewModel.presentationDialogStateFlow.collectLatest {
                when (it) {
                    ServiceShowState -> {
                        presentationDialog.show()
                    }

                    ServiceHideState -> {
                        presentationDialog.dismiss()
                    }
                }
            }

        }
    }

    override fun onStart(intent: Intent?, startId: Int) {
        mDispatcher.onServicePreSuperOnStart()
        super.onStart(intent, startId)
    }

    override fun onDestroy() {
        mDispatcher.onServicePreSuperOnDestroy()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun getLifecycle() = mDispatcher.lifecycle

    override fun getViewModelStore(): ViewModelStore = ApplicationViewModelStoreProvider

    inner class RemoteViewBinder : IPresentationAidlInterface.Stub() {
        override fun setRemoteView(remoteViews: RemoteViews) {
//            mAppViewModel.setPresentationScreenState(
//                PresentationScreenRoute.RemoteScreen(
//                    remoteViews
//                )
//            )
        }

    }
}