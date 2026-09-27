package com.example.demo.service;
import com.example.demo.dto.JobResponseDTO;
import com.example.demo.entity.Job;
import com.example.demo.repository.JobRepository;
import org.springframework.stereotype.Service;
import com.example.demo.exception.JobNotFoundException;
import com.example.demo.dto.JobRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
public class JobService {

    private final JobRepository jobRepository;

    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public JobResponseDTO createJob(Job job) {
        Job savedJob = jobRepository.save(job);
        return convertToDTO(savedJob);
    }

    public Page<JobResponseDTO> getAllJobs(Pageable pageable) {

        Page<Job> jobs = jobRepository.findAll(pageable);

        return jobs.map(this::convertToDTO);
    }

    public JobResponseDTO getJobById(Integer id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException("Job not found with id: " + id)
                );

        return convertToDTO(job);
    }

    public JobResponseDTO updateJob(Integer id, JobRequestDTO jobRequest) {

        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException("Job not found with id: " + id)
                );

        existingJob.setTitle(jobRequest.getTitle());
        existingJob.setDescription(jobRequest.getDescription());
        existingJob.setLocation(jobRequest.getLocation());
        existingJob.setSalary(jobRequest.getSalary());
        existingJob.setCompany(jobRequest.getCompany());

        Job savedJob = jobRepository.save(existingJob);

        return convertToDTO(savedJob);
    }
    public void deleteJob(Integer id) {

        if (!jobRepository.existsById(id)) {
            throw new JobNotFoundException(
                    "Job not found with id: " + id
            );
        }

        jobRepository.deleteById(id);
    }
    private JobResponseDTO convertToDTO(Job job) {

        JobResponseDTO response = new JobResponseDTO();

        response.setId(job.getId());
        response.setTitle(job.getTitle());
        response.setDescription(job.getDescription());
        response.setLocation(job.getLocation());
        response.setSalary(job.getSalary());
        response.setCompany(job.getCompany());

        return response;
    }
   
    public Page<JobResponseDTO> searchJobs(
            String title,
            String location,
            Pageable pageable) {

        Page<Job> jobs = jobRepository.searchJobs(
                title,
                location,
                pageable
        );

        return jobs.map(this::convertToDTO);
    }
}