package com.example.demo.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.demo.dto.JobRequestDTO;
import com.example.demo.entity.Job;
import com.example.demo.service.JobService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.example.demo.dto.JobResponseDTO;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public JobResponseDTO createJob(@Valid @RequestBody JobRequestDTO jobRequest) {

        Job job = new Job();

        job.setTitle(jobRequest.getTitle());
        job.setDescription(jobRequest.getDescription());
        job.setLocation(jobRequest.getLocation());
        job.setSalary(jobRequest.getSalary());
        job.setCompany(jobRequest.getCompany());

        return (JobResponseDTO) jobService.createJob(job);
    }
    

    
    @GetMapping
    public Page<JobResponseDTO> getAllJobs(Pageable pageable) {
        return jobService.getAllJobs(pageable);
    }
    
    
   
    @GetMapping("/{id}")
    public JobResponseDTO getJobById(@PathVariable Integer id) {
        return jobService.getJobById(id);
    }
    @PutMapping("/{id}")
    public JobResponseDTO updateJob(@PathVariable Integer id,
                                    @Valid @RequestBody JobRequestDTO jobRequest) {
        return jobService.updateJob(id, jobRequest);
    }
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Integer id) {

        jobService.deleteJob(id);

        return "Job deleted successfully";
    }
    
    @GetMapping("/search")
    public Page<JobResponseDTO> searchJobs(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location,
            Pageable pageable) {

        return jobService.searchJobs(title, location, pageable);
    }
    
}