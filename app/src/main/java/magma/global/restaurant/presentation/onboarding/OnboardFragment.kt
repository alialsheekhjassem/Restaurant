package magma.global.restaurant.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import magma.global.restaurant.R

class OnboardFragment : Fragment() {
    private lateinit var title: String
    private lateinit var description: String
    private var imageResource = 0
    private var position = 0
    private lateinit var tvTitle: TextView
    private lateinit var tvDescription: TextView
    private lateinit var imgBoard: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            position = requireArguments().getInt(ARG_PARAM1)
            title = requireArguments().getString(ARG_PARAM2)!!
            description = requireArguments().getString(ARG_PARAM3)!!
            imageResource = requireArguments().getInt(ARG_PARAM4)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val rootLayout: View =
            inflater.inflate(R.layout.fragment_onboarding, container, false)
        tvTitle = rootLayout.findViewById(R.id.txt_title)
        tvDescription = rootLayout.findViewById(R.id.txt_sub_title)
        imgBoard = rootLayout.findViewById(R.id.img_board)
        val btnStart: Button = rootLayout.findViewById(R.id.btn_start)
        tvTitle.text = title
        tvDescription.text = description
        imgBoard.setImageResource(imageResource)

        if (position != 0) {
            btnStart.visibility = View.GONE
        } else btnStart.visibility = View.VISIBLE

        return rootLayout
    }

    companion object {
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        private const val ARG_PARAM4 = "param4"
        fun newInstance(
            position: Int,
            title: String?,
            description: String?,
            imageResource: Int
        ): OnboardFragment {
            val fragment = OnboardFragment()
            val args = Bundle()
            args.putInt(ARG_PARAM1, position)
            args.putString(ARG_PARAM2, title)
            args.putString(ARG_PARAM3, description)
            args.putInt(ARG_PARAM4, imageResource)
            fragment.arguments = args
            return fragment
        }
    }
}