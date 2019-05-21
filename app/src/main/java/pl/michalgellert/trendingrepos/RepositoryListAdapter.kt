package pl.michalgellert.trendingrepos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pl.michalgellert.trendingrepos.model.Repository

class RepositoryListAdapter(private val list: List<Repository>) : RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

}

class RepositoryViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.repository_list_item, parent, false)) {
    private val repoName: TextView = itemView.findViewById(R.id.repoName_tv)
    private val repoDescription: TextView = itemView.findViewById(R.id.repoDescription_tv)
    private val repoAvatar: ImageView = itemView.findViewById(R.id.avatar_iv)
    private val repoOwner: TextView = itemView.findViewById(R.id.repoOwner_tv)
    private val repoStars: TextView = itemView.findViewById(R.id.repoStars_tv)

    fun bind(repository: Repository) {
        repoName.text = repository.name
        repoDescription.text = repository.description
        Picasso.get().load(repository.avatar).into(repoAvatar)
        repoOwner.text = repository.username
        repoStars.text = repository.stars.toString()
    }

}