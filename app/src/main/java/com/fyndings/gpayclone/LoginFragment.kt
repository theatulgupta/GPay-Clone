package com.fyndings.gpayclone

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.fyndings.gpayclone.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false);

//        Initializing auth
        auth = FirebaseAuth.getInstance()

        var phoneNumber = binding.etPhoneNumber.text.trim().toString()
        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                binding.btnContinue.background.setTint(resources.getColor(R.color.theme_blue))
                binding.btnContinue.setTextColor(resources.getColor(R.color.white))
            }
        })

        binding.btnContinue.setOnClickListener {
            if (phoneNumber.isNotEmpty()) {
                if (phoneNumber.length == 10) {
                    phoneNumber = "+91$phoneNumber"

                    val options = PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this.requireActivity())                 // Activity (for callback binding)
                        .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                        .build()
                    PhoneAuthProvider.verifyPhoneNumber(options)

                } else {
                    Toast.makeText(this.activity,
                        "Please enter valid phone number",
                        Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this.activity, "Please enter phone number", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return binding.root
    }

    //    signInWithPhoneAuthCredential
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    findNavController().navigate(R.id.action_splashScreenFragment2_to_dashboardFragment)
                    Toast.makeText(this.activity,
                        "Authentication Successful",
                        Toast.LENGTH_SHORT).show()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d(TAG,
                        "signInWithPhoneAuthCredential: {${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    //    Handling callbacks
    private val callbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Log.d("TAG", "onVerificationFailed: ${e.toString()}")
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    Log.d("TAG", "onVerificationFailed: ${e.toString()}")
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken,
            ) {
                val bundle = Bundle()
                bundle.putString("OTP", verificationId)
                bundle.putString("phoneNumber", binding.etPhoneNumber.text.toString())
                bundle.putString("resendToken", token.toString())
                findNavController().navigate(R.id.action_loginFragment_to_OTPFragment,
                    bundle)
            }
        }
}