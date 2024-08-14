import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.otakkanan.taskapp.R

class ViewPagerAdapter(
    private var image: List<Int>,
    private var title: List<String>,
    private var desc: List<String>,
    private var highlightWords: List<String>
) : RecyclerView.Adapter<ViewPagerAdapter.Pager2ViewHolder>() {

    inner class Pager2ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.title)
        val itemDesc: TextView = itemView.findViewById(R.id.desc)
        val itemImage: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Pager2ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_container_onboarding, parent, false)
        return Pager2ViewHolder(view)
    }

    override fun onBindViewHolder(holder: Pager2ViewHolder, position: Int) {
        holder.itemImage.setImageResource(image[position])
        holder.itemTitle.text = title[position]

        // Get the description and the word to highlight
        val descText = desc[position]
        val wordToHighlight = highlightWords[position]

        // Create a SpannableString to style the word
        val spannableString = SpannableString(descText)

        val startIndex = descText.indexOf(wordToHighlight)
        if (startIndex != -1) {
            // Apply color and bold style to the word
            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(holder.itemView.context, R.color.md_theme_onBoarding)),
                startIndex, startIndex + wordToHighlight.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannableString.setSpan(
                StyleSpan(Typeface.BOLD),
                startIndex, startIndex + wordToHighlight.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        holder.itemDesc.text = spannableString
    }

    override fun getItemCount(): Int {
        return title.size
    }
}