package com.example.kotlinmvvm.view.dialog

import android.view.ViewGroup
import com.example.kotlinmvvm.base.BaseDialog
import com.example.kotlinmvvm.databinding.DialogDeleteFoodBinding

class DeleteDialog(private var listener: OnClickListener): BaseDialog<DialogDeleteFoodBinding>() {

    override fun getLayout(container: ViewGroup?): DialogDeleteFoodBinding =
        DialogDeleteFoodBinding.inflate(layoutInflater, container, false)

    override fun initViews() {
        onDeleteNote()
        onNoDelete()
    }

    private fun onNoDelete() {
        binding.btnNo.setOnClickListener {
            dialog?.cancel()
        }
    }

    private fun onDeleteNote() {
        binding.btnYes.setOnClickListener {
            listener.onClick()
            dismiss()
        }
    }
}