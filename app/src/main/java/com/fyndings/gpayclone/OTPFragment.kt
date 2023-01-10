package com.fyndings.gpayclone

import android.content.ContentValues
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.fyndings.gpayclone.databinding.FragmentLoginBinding
import com.fyndings.gpayclone.databinding.FragmentOTPBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPFragment : Fragment() {
    private var _binding: FragmentOTPBinding? = null
    val inputMethodManager =
        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    private lateinit var auth: FirebaseAuth

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentOTPBinding.inflate(inflater, container, false);

        binding.progressBar.visibility = View.VISIBLE
        auth = FirebaseAuth.getInstance()


        val otp = arguments?.getString("OTP").toString()
        val resendToken = arguments?.getString("resendToken")
        val phoneNumber = arguments?.getString("phoneNumber")
        binding.txtOtp.text = "Enter the OTP sent to +91 $phoneNumber"
        binding.progressBar.visibility = View.GONE

        // time count down for 30 seconds,
        // with 1 second as countDown interval
        object : CountDownTimer(30000, 1000) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                binding.txtTimer.text =
                    "Having trouble? Request a new OTP in 00:" + (millisUntilFinished / 1000).toString()
            }

            // Callback function, fired when the time is up
            override fun onFinish() {
                binding.txtTimer.text = "Resend OTP"
                binding.txtTimer.setTextColor(resources.getColor(R.color.theme_blue))
                binding.txtTimer.setTextAppearance(requireContext(),
                    androidx.appcompat.R.style.TextAppearance_AppCompat_Title);
            }
        }.start()

        editTextChangedListener()

        binding.btnVerify.setOnClickListener {
//            collect otp from all the edit text
            val typedOTP = binding.otp1.text.toString() +
                    binding.otp2.text.toString() +
                    binding.otp3.text.toString() +
                    binding.otp4.text.toString() +
                    binding.otp5.text.toString() +
                    binding.otp6.text.toString()

            if (typedOTP.isNotEmpty()) {
                if (typedOTP.length == 6) {
                    val credential: PhoneAuthCredential =
                        PhoneAuthProvider.getCredential(otp, typedOTP)
                    signInWithPhoneAuthCredential(credential)

                } else Toast.makeText(this.activity, "Please enter OTP", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this.activity, "Please enter correct OTP", Toast.LENGTH_SHORT)
                .show()
        }
        return binding.root
    }

    //    signInWithPhoneAuthCredential
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    findNavController().navigate(R.id.action_OTPFragment_to_dashboardFragment)
                    Toast.makeText(this.activity,
                        "Authentication Successful",
                        Toast.LENGTH_SHORT).show()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d(ContentValues.TAG,
                        "signInWithPhoneAuthCredential: {${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    //        Text Change Listener
    private fun editTextChangedListener() {
        binding.otp1.addTextChangedListener { EditTextWatcher(binding.otp1) }
        binding.otp2.addTextChangedListener { EditTextWatcher(binding.otp2) }
        binding.otp3.addTextChangedListener { EditTextWatcher(binding.otp3) }
        binding.otp4.addTextChangedListener { EditTextWatcher(binding.otp4) }
        binding.otp5.addTextChangedListener { EditTextWatcher(binding.otp5) }
        binding.otp6.addTextChangedListener { EditTextWatcher(binding.otp6) }
    }

    //        Text-Watcher Feature
    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()
            when (view.id) {
                R.id.otp1 -> if (text.length == 1) binding.otp2.requestFocus()
                R.id.otp2 -> if (text.length == 1) binding.otp3.requestFocus() else if (text.isEmpty()) binding.otp1.requestFocus()
                R.id.otp3 -> if (text.length == 1) binding.otp4.requestFocus() else if (text.isEmpty()) binding.otp2.requestFocus()
                R.id.otp4 -> if (text.length == 1) binding.otp5.requestFocus() else if (text.isEmpty()) binding.otp3.requestFocus()
                R.id.otp5 -> if (text.length == 1) binding.otp6.requestFocus() else if (text.isEmpty()) binding.otp4.requestFocus()
                R.id.otp6 -> if (text.length == 1) {
                    if (text.isEmpty()) {
                        binding.otp5.requestFocus()
                    }
                    binding.btnVerify.background.setTint(resources.getColor(R.color.theme_blue))
                    binding.btnVerify.setTextColor(resources.getColor(R.color.white))
                    view.hideKeyboard(inputMethodManager)
                }
            }
        }

    }

    fun View.hideKeyboard(inputMethodManager: InputMethodManager) {
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}