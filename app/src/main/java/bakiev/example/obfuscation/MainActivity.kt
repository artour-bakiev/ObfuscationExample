package bakiev.example.obfuscation

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import bakiev.example.obfuscation.network.api.GithubService
import bakiev.example.obfuscation.databinding.ActivityMainBinding
import bakiev.example.obfuscation.databinding.RepoItemBinding
import bakiev.example.obfuscation.network.pdo.Repo
import dagger.android.AndroidInjection
import javax.inject.Inject
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    @Inject
    internal lateinit var githubService: GithubService
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                query(view)
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                query(view)
                true
            } else {
                false
            }
        }
    }

    private fun query(v: View) = lifecycleScope.launch {
        val query = binding.input.text.toString()
        if (query.isEmpty()) {
            return@launch
        }
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
        binding.progressBar.visibility = View.VISIBLE
        val repos = githubService.searchRepos(query)
        binding.progressBar.visibility = View.GONE
        binding.repoList.adapter = RepoAdapter(repos.items, layoutInflater)
        binding.repoList.visibility = View.VISIBLE
    }

    private class RepoViewHolder(val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root)

    private class RepoAdapter(private val repos: List<Repo>, private val inflater: LayoutInflater) :
        RecyclerView.Adapter<RepoViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
            val binding = RepoItemBinding.inflate(inflater, parent, false)
            return RepoViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
            holder.binding.repo = repos[position]
        }

        override fun getItemCount(): Int = repos.size
    }
}
