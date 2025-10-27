package co.facilite.devjr.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// for static metamodels
import co.facilite.devjr.domain.Employee;
import co.facilite.devjr.repository.EmployeeRepository;
import co.facilite.devjr.service.criteria.EmployeeCriteria;
import co.facilite.devjr.service.dto.EmployeeDTO;
import co.facilite.devjr.service.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Employee} entities in the
 * database. The main input is a {@link EmployeeCriteria} which gets converted
 * to {@link Specification}, in a way that all the filters must apply. It
 * returns a {@link Page} of {@link EmployeeDTO} which fulfills the criteria.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeQueryService extends QueryService<Employee> {

	private final EmployeeRepository employeeRepository;

//	private final EmployeeMapper employeeMapper;

	/**
	 * Return a {@link Page} of {@link EmployeeDTO} which matches the criteria from
	 * the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @param page     The page, which should be returned.
	 * @return the matching entities.
	 */
	@Transactional(readOnly = true)
	public Page<EmployeeDTO> findByCriteria(EmployeeCriteria criteria, Pageable page) {
		log.debug("find by criteria : {}, page: {}", criteria, page);
		final Specification<Employee> specification = createSpecification(criteria);
		return null;
//		return employeeRepository.findAll(specification, page).map(employeeMapper::toDto);
	}

	/**
	 * Return the number of matching entities in the database.
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the number of matching entities.
	 */
	@Transactional(readOnly = true)
	public long countByCriteria(EmployeeCriteria criteria) {
		log.debug("count by criteria : {}", criteria);
		final Specification<Employee> specification = createSpecification(criteria);
		return employeeRepository.count(specification);
	}

	/**
	 * Function to convert {@link EmployeeCriteria} to a {@link Specification}
	 * 
	 * @param criteria The object which holds all the filters, which the entities
	 *                 should match.
	 * @return the matching {@link Specification} of the entity.
	 */
	protected Specification<Employee> createSpecification(EmployeeCriteria criteria) {
//		Specification<Employee> specification = Specification.where(null);
//		if (criteria != null) {
//			specification = Specification.allOf(
//					Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
//
//					// campos simples
//					buildRangeSpecification(criteria.getId(), "id"),
//					buildStringSpecification(criteria.getFirstName(), "firstName"),
//					buildStringSpecification(criteria.getLastName(), "lastName"),
//					buildStringSpecification(criteria.getEmail(), "email"),
//					buildStringSpecification(criteria.getPhone(), "phone"),
//					buildRangeSpecification(criteria.getHireDate(), "hireDate"),
//					buildRangeSpecification(criteria.getSalary(), "salary"),
//					buildSpecification(criteria.getActive(), "active"),
//
//					// relacionamentos (joins por nome)
//					buildSpecification(criteria.getAddressId(), root -> root.join("address", JoinType.LEFT).get("id")),
//					buildSpecification(criteria.getDepartmentId(),
//							root -> root.join("department", JoinType.LEFT).get("id")));
//		}
		return null;
//		return specification;
	}
}
