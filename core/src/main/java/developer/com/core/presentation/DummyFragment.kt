package developer.com.core.presentation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import developer.com.core.R
import developer.com.core.presentation.base.BaseFragment
import developer.com.core.presentation.util.Constant

class DummyFragment : BaseFragment() {
    companion object {
        private const val EXTRA = "EXTRA"
        fun newInstance(text: String) = DummyFragment().apply {
            arguments = Bundle(1).also {
                it.putString(EXTRA, text)
            }
        }
    }

    override val layoutResId = R.layout.fragment_dummy
    override val toolbarTitle
        get() = arguments?.getString(EXTRA, Constant.UNKNOWN) ?: Constant.UNKNOWN

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = toolbarTitle
        view.findViewById<TextView>(R.id.textView).text = title
    }
}