package org.quidcain.jad.backend.repository;

import org.quidcain.jad.backend.model.Sample;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SampleRepository extends JpaRepository<Sample, Long> {
}
