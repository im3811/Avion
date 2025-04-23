package main.java.travelfinder.view;

import java.time.LocalDate;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;
import main.java.travelfinder.model.Booking;
import main.java.travelfinder.utils.AlertUtil;

import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
interface BookingFormSubmitHandler {
    void onSubmit(LocalDate checkInDate, LocalDate checkOutDate, int numGuests, String specialRequests);
}

public class BookingView {
    
    private final Stage stage;
    private TabPane tabPane;
    private VBox bookingsContainer;
    private BookingFormSubmitHandler onConfirmBooking;
    private Consumer<Integer> onCancelBooking;
    
    public BookingView() {
        this.stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Bookings");
        stage.setMinWidth(600);
        stage.setMinHeight(400);
    }
    
    public void setupBookingForm(int accommodationId, int roomId) {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));
        
        // Form header
        Label headerLabel = new Label("Book Your Stay");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        // Form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 0, 20, 0));
        
        Label checkInLabel = new Label("Check-in Date:");
        grid.add(checkInLabel, 0, 0);
        
        DatePicker checkInPicker = new DatePicker();
        checkInPicker.setValue(LocalDate.now().plusDays(1));
        grid.add(checkInPicker, 1, 0);
        
        Label checkOutLabel = new Label("Check-out Date:");
        grid.add(checkOutLabel, 0, 1);
        
        DatePicker checkOutPicker = new DatePicker();
        checkOutPicker.setValue(LocalDate.now().plusDays(3));
        grid.add(checkOutPicker, 1, 1);
        
        Label guestsLabel = new Label("Number of Guests:");
        grid.add(guestsLabel, 0, 2);
        
        Spinner<Integer> guestsSpinner = new Spinner<>(1, 10, 2);
        grid.add(guestsSpinner, 1, 2);
        
        Label specialRequestsLabel = new Label("Special Requests:");
        grid.add(specialRequestsLabel, 0, 3);
        
        TextArea specialRequestsArea = new TextArea();
        specialRequestsArea.setPrefRowCount(3);
        specialRequestsArea.setWrapText(true);
        grid.add(specialRequestsArea, 1, 3);
        
        // Button box
        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        
        Button confirmButton = new Button("Confirm Booking");
        confirmButton.setOnAction(e -> {
            if (onConfirmBooking != null) {
                if (checkInPicker.getValue() == null || checkOutPicker.getValue() == null) {
                    AlertUtil.showError("Invalid Dates", "Please select check-in and check-out dates");
                    return;
                }
                
                onConfirmBooking.onSubmit(
                    checkInPicker.getValue(),
                    checkOutPicker.getValue(),
                    guestsSpinner.getValue(),
                    specialRequestsArea.getText()
                );
            }
        });
        
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> stage.close());
        
        buttonBox.getChildren().addAll(confirmButton, cancelButton);
        
        // Assemble layout
        VBox container = new VBox(10);
        container.getChildren().addAll(headerLabel, grid, buttonBox);
        
        layout.setCenter(container);
        
        Scene scene = new Scene(layout);
        stage.setScene(scene);
    }
    
    public void displayUserBookings(List<Booking> bookings) {
        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(10));
        
        // Header
        Label headerLabel = new Label("My Bookings");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        
        // Bookings container
        bookingsContainer = new VBox(10);
        bookingsContainer.setPadding(new Insets(10, 0, 10, 0));
        
        if (bookings.isEmpty()) {
            Label noBookingsLabel = new Label("You don't have any bookings");
            bookingsContainer.getChildren().add(noBookingsLabel);
        } else {
            populateBookings(bookings);
        }
        
        ScrollPane scrollPane = new ScrollPane(bookingsContainer);
        scrollPane.setFitToWidth(true);
        
        // Button
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> stage.close());
        
        // Assemble layout
        VBox container = new VBox(10);
        container.getChildren().addAll(headerLabel, scrollPane);
        
        layout.setCenter(container);
        layout.setBottom(closeButton);
        
        Scene scene = new Scene(layout, 600, 400);
        stage.setScene(scene);
    }
    
    private void populateBookings(List<Booking> bookings) {
        bookingsContainer.getChildren().clear();
        
        for (Booking booking : bookings) {
            VBox bookingBox = new VBox(5);
            bookingBox.setPadding(new Insets(10));
            bookingBox.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5;");
            
            Label referenceLabel = new Label("Booking Ref: " + booking.getReferenceNumber());
            referenceLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
            
            Label accommodationLabel = new Label("Accommodation: " + 
                (booking.getAccommodation() != null ? booking.getAccommodation().getName() : "Unknown"));
            
            Label datesLabel = new Label("Dates: " + booking.getCheckInDate() + " to " + booking.getCheckOutDate() + 
                                        " (" + booking.getNights() + " nights)");
            
            Label guestsLabel = new Label("Guests: " + booking.getNumGuests());
            
            Label statusLabel = new Label("Status: " + booking.getStatusName());
            
            Label priceLabel = new Label(String.format("Total Price: $%.2f", booking.getTotalPrice()));
            
            HBox buttonBox = new HBox(10);
            
            if (booking.isCancellable()) {
                Button cancelButton = new Button("Cancel Booking");
                cancelButton.setOnAction(e -> {
                    boolean confirm = AlertUtil.showConfirmation(
                        "Cancel Booking", 
                        "Are you sure you want to cancel this booking?"
                    );
                    
                    if (confirm && onCancelBooking != null) {
                        onCancelBooking.accept(booking.getBookingId());
                    }
                });
                buttonBox.getChildren().add(cancelButton);
            }
            
            bookingBox.getChildren().addAll(
                referenceLabel, accommodationLabel, datesLabel, 
                guestsLabel, statusLabel, priceLabel, buttonBox
            );
            
            bookingsContainer.getChildren().add(bookingBox);
        }
    }
    
    public void show() {
        stage.show();
    }
    
    public void close() {
        stage.close();
    }
    
    public void setOnConfirmBooking(BookingFormSubmitHandler onConfirmBooking) {
        this.onConfirmBooking = onConfirmBooking;
    }
    
    public void setOnCancelBooking(Consumer<Integer> onCancelBooking) {
        this.onCancelBooking = onCancelBooking;
    }

        @FunctionalInterface
    public interface BookingFormSubmitHandler {
        void onSubmit(LocalDate checkInDate, LocalDate checkOutDate, int numGuests, String specialRequests);
    }


}