package pl.michalgellert.trendingrepos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import pl.michalgellert.trendingrepos.model.Repository

class RepositoryListAdapter(private val list: List<Repository>)
    : RecyclerView.Adapter<RepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RepositoryViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repo: Repository = list[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int = list.size

}

class RepositoryViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.repository_list_item, parent, false)) {
    private var repoName: TextView = itemView.findViewById(R.id.repoName_tv)
    private var repoDescription: TextView = itemView.findViewById(R.id.repoDescription_tv)
    private var repoAvatar: ImageView = itemView.findViewById(R.id.avatar_iv)
    private var repoOwner: TextView = itemView.findViewById(R.id.repoOwner_tv)
    private var repoStars: TextView = itemView.findViewById(R.id.repoStars_tv)

    fun bind(repository: Repository) {
        repoName.text = repository.name
        repoDescription.text = repository.description
        Picasso.get().load(repository.avatar).into(repoAvatar)
        repoOwner.text = repository.username
        repoStars.text = repository.stars.toString()
    }

}