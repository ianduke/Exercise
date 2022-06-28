package com.example.fitbodinterview.ui.exercisedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.fitbodinterview.R
import com.example.fitbodinterview.databinding.FragmentExerciseDetailBinding
import com.example.fitbodinterview.ui.MainActivity
import com.example.fitbodinterview.utils.Logger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExerciseDetailFragment : Fragment() {

    private val exerciseDetailViewModel: ExerciseDetailViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    private var _binding: FragmentExerciseDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ExerciseDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            exerciseDetailViewModel.getExerciseListItem(args.exerciseId)
                .collect { exerciseListItem ->
                    _binding?.exerciseListItem?.exerciseName?.text = exerciseListItem.exerciseName
                    _binding?.exerciseListItem?.weight?.text =
                        exerciseListItem.oneRmRecord.toString()
                }

        }

        lifecycleScope.launch {
            exerciseDetailViewModel.graphPoints(args.exerciseId)
                .catch { Logger.w("XXXX: Error getting exercise records", it) }
                .collect { graphPoints ->
                    Logger.d("XXXX: addding ${graphPoints.size} records to graph")
                    _binding?.graphView?.isVisible = graphPoints.size > 1
                    _binding?.graphView?.setData(graphPoints)
                }
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            exerciseDetailViewModel.getExerciseListItem(exerciseId = args.exerciseId)
                .collect {
                    val activity: MainActivity? = activity as? MainActivity
                    activity?.supportActionBar?.title = it.exerciseName
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}