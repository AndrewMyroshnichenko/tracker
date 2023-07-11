package com.example.tracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.tracker.R
import com.example.tracker.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment(), View.OnClickListener {

    lateinit var mAuth: FirebaseAuth

    //private var viewModel: LoginViewModel? = vm
    private var bind: FragmentLoginBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentLoginBinding.inflate(inflater, container, false)
        return bind?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        bind?.btSingInUp?.setOnClickListener(this)
        bind?.tvForgotPassword?.setOnClickListener(this)
        bind?.widgetSingInUp?.setOnClickListener(this)
        bind?.ibBack?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bt_sing_in_up -> clickOnButtonSingInUp(v)
            R.id.tv_forgot_password -> clickOnForgotPassword()
            R.id.widget_sing_in_up -> clickOnSingInUp()
            R.id.ib_back -> backToLoginViews()
        }
    }

    private fun clickOnButtonSingInUp(v: View) {
        val userEmail = bind?.edUserEmail?.text.toString()
        val userPassword = bind?.edPassword?.text.toString()
        val confirmPassword = bind?.edConfirmPassword?.text.toString()
        val buttonText = bind?.btSingInUp?.text.toString()

        if (buttonText == resources.getString(R.string.sing_in)) {
            if (userEmail.isEmpty() || userPassword.isEmpty()) {
                showMessage(getString(R.string.msg_fill_all_fields), v)
            } else {
                //viewModel?.send(SingInEvent(userEmail, userPassword))
                mAuth.signInWithEmailAndPassword(userEmail, userPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.action_loginFragment_to_trackerFragment)
                        } else {
                            Toast.makeText(requireContext(), "failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        } else if (buttonText == resources.getString(R.string.sing_up)) {
            if (userEmail.isEmpty() || userPassword.isEmpty() || confirmPassword.isEmpty()) {
                showMessage(getString(R.string.msg_fill_all_fields), v)
            } else if (userPassword != confirmPassword) {
                showMessage(getString(R.string.msg_pass_must_be_same), v)
            } else {
                //viewModel?.send(SingUpEvent(userEmail, userPassword))
            }
        } else {
            //viewModel?.send(ForgotPasswordEvent(userEmail))
        }
    }

    private fun clickOnForgotPassword() {
        doInvisibleNonForgotPasswordFields()
        bind?.btSingInUp?.text = resources.getString(R.string.send_new_password)
        bind?.tvSignTitle?.text = resources.getString(R.string.forgot_password)
        bind?.ibBack?.visibility = View.VISIBLE
    }

    private fun clickOnSingInUp() {
        if (bind?.tvSingInUp?.text == (resources.getString(R.string.sing_up))) {
            setUpLogInUpViewsProperties(
                View.GONE, View.VISIBLE,
                resources.getString(R.string.sing_up),
                resources.getString(R.string.sing_in),
                resources.getString(R.string.already_have_an_account)
            )
        } else {
            setUpLogInUpViewsProperties(
                View.VISIBLE, View.GONE,
                resources.getString(R.string.sing_in),
                resources.getString(R.string.sing_up),
                resources.getString(R.string.don_t_have_an_account)
            )
        }
    }

    private fun doInvisibleNonForgotPasswordFields() {
        bind?.inputFieldPassword?.visibility = View.GONE
        bind?.tvForgotPassword?.visibility = View.GONE
        bind?.tvQuestion?.visibility = View.GONE
        bind?.tvSingInUp?.visibility = View.GONE
    }

    private fun setUpLogInUpViewsProperties(
        visibilityForgotPassword: Int,
        visibilityConfirmPassword: Int,
        titleOfToolbarAndButton: String,
        bottomTextMenu: String,
        bottomTextQuestion: String,
    ) {
        bind?.tvForgotPassword?.visibility = visibilityForgotPassword
        bind?.inputFieldConfirmPassword?.visibility = visibilityConfirmPassword
        bind?.ibBack?.visibility = View.GONE
        bind?.btSingInUp?.text = titleOfToolbarAndButton
        bind?.tvSingInUp?.text = bottomTextMenu
        bind?.tvQuestion?.text = bottomTextQuestion
        bind?.tvSignTitle?.text = titleOfToolbarAndButton

    }

    private fun showMessage(message: String, view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun backToLoginViews() {
        bind?.inputFieldPassword?.visibility = View.VISIBLE
        bind?.tvForgotPassword?.visibility = View.VISIBLE
        bind?.tvQuestion?.visibility = View.VISIBLE
        bind?.tvSingInUp?.visibility = View.VISIBLE
        bind?.inputFieldConfirmPassword?.visibility = View.GONE
        bind?.ibBack?.visibility = View.GONE
        bind?.btSingInUp?.text = resources.getString(R.string.sing_in)
        bind?.tvSingInUp?.text = resources.getString(R.string.sing_up)
        bind?.tvQuestion?.text = resources.getString(R.string.don_t_have_an_account)
        bind?.tvSignTitle?.text = resources.getString(R.string.sing_up)
    }


}