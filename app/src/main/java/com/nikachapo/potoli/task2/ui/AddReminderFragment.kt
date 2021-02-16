package com.nikachapo.potoli.task2.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.nikachapo.potoli.R
import com.nikachapo.potoli.task2.ReminderModel
import com.nikachapo.potoli.task2.ui.time_picker.TimePickerFragment

class AddReminderFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var listeners: AddReminderFragmentListeners

    private var hour = 0
    private var minute = 0

    private val timeTV: TextView by lazy { view!!.findViewById<TextView>(R.id.timeTV) }
    private val chooseTimeBtn: Button by lazy { view!!.findViewById<Button>(R.id.chooseTimeBtn) }
    private val repeatCB: CheckBox by lazy { view!!.findViewById<CheckBox>(R.id.repeatCB) }
    private val saveBtn: Button by lazy { view!!.findViewById<Button>(R.id.saveBtn) }
    private val cancelBtn: Button by lazy { view!!.findViewById<Button>(R.id.cancelBtn) }
    private val nameET: EditText by lazy { view!!.findViewById<EditText>(R.id.nameET) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listeners = (context as AddReminderFragmentListeners)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_reminder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseTimeBtn.setOnClickListener {
            val timePicker = TimePickerFragment()
            timePicker.show(childFragmentManager, "time picker")
        }

        saveBtn.setOnClickListener {

            listeners.onSaveReminder(
                ReminderModel(
                    hour,
                    minute,
                    nameET.text.toString(),
                    repeatCB.isChecked
                )
            )
            dismiss()
        }

        cancelBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(width, height)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        this.hour = hourOfDay
        this.minute = minute
        timeTV.text = "$hourOfDay:$minute"
    }

    interface AddReminderFragmentListeners {
        fun onSaveReminder(reminder: ReminderModel)
    }

}