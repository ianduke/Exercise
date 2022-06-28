package com.example.fitbodinterview.ui.exerciselist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.fitbodinterview.R
import com.example.fitbodinterview.databinding.FragmentExerciseListBinding
import com.example.fitbodinterview.ui.MainActivity
import com.example.fitbodinterview.ui.exercisedetail.ExerciseDetailFragmentArgs
import com.example.fitbodinterview.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExerciseListFragment : Fragment() {

    private val exerciseListViewModel: ExerciseListViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private lateinit var adapter: ExerciseListAdapter

    private var _binding: FragmentExerciseListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = ExerciseListAdapter(object : ExerciseListAdapter.OnExerciseClickListener {
            override fun onExerciseClicked(exerciseId: Long) {
                findNavController().navigate(
                    R.id.action_exerciseListFragment_to_exerciseDetailFragment,
                    ExerciseDetailFragmentArgs(exerciseId).toBundle()
                )
            }
        })
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {
            exerciseListViewModel.getExercises()
                .catch { Logger.w("Error getting exercise list", it) }
                .collect { items ->
                    adapter.swapData(items)
                }
        }
    }

    override fun onResume() {
        super.onResume()
        val activity: MainActivity? = activity as? MainActivity
        val actionBar = activity?.supportActionBar
        actionBar?.title = ""
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}