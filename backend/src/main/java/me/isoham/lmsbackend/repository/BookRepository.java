package me.isoham.lmsbackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import me.isoham.lmsbackend.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
	List<Book> findByActiveTrue();

	Optional<Book> findByIdAndActiveTrue(Long id);

	boolean existsByTitleAndAuthor(String name, String author);

	@Modifying
	@Query("""
			UPDATE Book b
			SET b.availableQuantity = b.availableQuantity - :quantity
			WHERE b.id = :bookId
			AND b.availableQuantity >= :quantity
			""")
	int decrementAvailableQuantity(@Param("bookId") Long bookId, @Param("quantity") int quantity);

	@Modifying
	@Query("""
			UPDATE Book b
			SET b.availableQuantity = b.availableQuantity + :quantity
			WHERE b.id = :bookId
			""")
	int incrementAvailableQuantity(@Param("bookId") Long bookId, @Param("quantity") int quantity);

	@Modifying
	@Query("""
			UPDATE Book b
			SET b.availableQuantity = b.availableQuantity - :quantity,
				b.quantity = b.quantity - :quantity
			WHERE b.id = :bookId
			AND b.availableQuantity >= :quantity
			""")
	int decrementQuantity(@Param("bookId") Long bookId, @Param("quantity") int quantity);

	@Modifying
	@Query("""
			UPDATE Book b
			SET b.availableQuantity = b.availableQuantity + :quantity,
				b.quantity = b.quantity + :quantity
			WHERE b.id = :bookId
			""")
	int incrementQuantity(@Param("bookId") Long bookId, @Param("quantity") int quantity);

}
