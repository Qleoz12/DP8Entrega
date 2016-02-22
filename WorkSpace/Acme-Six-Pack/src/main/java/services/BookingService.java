package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Booking;
import domain.Customer;

import repositories.BookingRepository;

@Service
@Transactional
public class BookingService {
	
	// Managed repository -----------------------------------------------------
	
	@Autowired
	private BookingRepository bookingRepository;
	
	// Supporting services ----------------------------------------------------

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private CustomerService customerService;
	
	// Constructors -----------------------------------------------------------

	public BookingService() {
		super();
	}
	
	// Simple CRUD methods ----------------------------------------------------

	/**
	 * 
	 * @return Devuelve booking preparado para ser modificado. Necesita usar save para que persista en la base de datos.
	 */
	// Requisito 10.3
	public Booking create(){
		
		Assert.isTrue(actorService.checkAuthority("CUSTOMER"), "Only the customer can book a booking");
		
		Booking result;
				
		result = new Booking();

		return result;
	}
	
	/**
	 * 
	 * @param booking
	 * 
	 * Guarda un booking creado o modificado
	 */
	// Requisito 10.3 - Book a service as long as he or she has paid the corresponding fee.
	public void save(Booking booking){
		
		Assert.notNull(booking);
		Assert.isTrue(actorService.checkAuthority("CUSTOMER"), "Only a customer can book services");
		
		booking.setCreationMoment(new Date());
		booking.setApproved(false);
		booking.setDenied(false);
		booking.setCanceled(false);
		
		Assert.isTrue(booking.getCreationMoment().before(booking.getRequestMoment()), "The request moment must be after creation moment");
		Assert.isTrue(isDurationValid(booking.getDuration()), "The duration of the booking must be expressed in hours or half-hours");
		
		bookingRepository.save(booking);
		
	}
	
	/**
	 * 
	 * @param booking
	 * 
	 * Marca como cancelado un booking
	 */
	// Requisito 10.4 - Cancel a booking as long as no administrator has approved or denied it.
	public void cancel(Booking booking){
		
		Assert.notNull(booking);
		Assert.isTrue(booking.getId() != 0);
		Assert.isTrue(actorService.checkAuthority("CUSTOMER"), "Only a customer can cancel a booking");
		Assert.isTrue(booking.getApproved() == false, "The selected booking is already approved");
		Assert.isTrue(booking.getDenied() == false, "The selected booking is already denied");
		Assert.isTrue(booking.getCanceled() == false, "The selected booking is already canceled");

		booking.setCanceled(true);
		bookingRepository.save(booking);
		
	}
	
	/**
	 * 
	 * @return Devuelve todos los booking de la base de datos
	 */
	public Collection<Booking> findAll(){
		
		Collection<Booking> result;
		
		result = bookingRepository.findAll();
		
		return result;
	}
	
	/**
	 * 
	 * @return Devuelve todos los booking de un customer en concreto
	 */
	public Collection<Booking> findAllByCustomer(){
		
		Collection<Booking> result;
		Customer customer;
		
		customer = customerService.findByPrincipal();
		Assert.notNull(customer);
		
		result = bookingRepository.findAllByCustomer(customer.getId());
		
		return result;
	}
	
	/**
	 * 
	 * @param bookingId Id del Booking en cuesti�n
	 * @return Devuelve el booking en cuesti�n
	 */
	public Booking findOne(int bookingId){
		
		Booking result;
		
		result = bookingRepository.findOne(bookingId);
		
		return result;
	}
	
	// Other business methods -------------------------------------------------

	/**
	 * 
	 * @param booking
	 * 
	 * Marca como aprobado un booking
	 */
	// Requisito 11.2 - Approve or deny a service booking.
	public void approveBooking(Booking booking){
		
		Assert.notNull(booking);
		Assert.isTrue(booking.getId() != 0);
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "Only an admin can approve a booking");
		Assert.isTrue(!booking.getApproved(), "The selected booking is already approved");
		Assert.isTrue(!booking.getDenied(), "The selected booking is already denied");
		Assert.isTrue(!booking.getCanceled(), "The selected booking is already canceled");
		
		booking.setApproved(true);
		bookingRepository.save(booking);
		
	}
	
	/**
	 * 
	 * @param booking
	 * 
	 * Marca como denegado un booking
	 */
	// Requisito 11.2 - Approve or deny a service booking.
	public void denyBooking(Booking booking){
		
		Assert.notNull(booking);
		Assert.isTrue(booking.getId() != 0);
		Assert.isTrue(actorService.checkAuthority("ADMIN"), "Only an admin can approve a booking");
		Assert.isTrue(!booking.getApproved(), "The selected booking is already approved");
		Assert.isTrue(!booking.getDenied(), "The selected booking is already denied");
		Assert.isTrue(!booking.getCanceled(), "The selected booking is already canceled");
		
		booking.setDenied(true);
		bookingRepository.save(booking);
		
	}
	
	private boolean isDurationValid(double duration){
		
		Boolean result;
		Double d;
		Double mod;
		
		result = false;
		d = 0.0;
		mod = 0.0;
		
		d = duration*10.0;
		mod = d % 5.0;
		d = d/5.0;
		
		if (d - mod == 0.0){
			result = true;
		}
		
		return result;
	}
}
