package main.java.travelfinder.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.java.travelfinder.utils.AlertUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * View class for booking screen
 */
public class BookingView {
    
    private final Stage primaryStage;
    private Scene bookingScene;
    
    // UI components
    private Text accommodationText;
    private Text datesText;
    private Text guestsText;
    private Text totalPriceText;
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextField phoneField;
    private TextArea specialRequestsArea;
    private ComboBox<String> paymentMethodCombo;
    private VBox creditCardForm;
    private CheckBox termsCheckBox;
    
    // Event handlers
    private Runnable onBackButtonClicked;
    private Consumer<BookingDetails> onCompleteBookingClicked;
    
    // Booking information
    private String accommodationName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String guests;
    private long nights;
    private BigDecimal basePrice;
    private BigDecimal totalBeforeTax;
    private BigDecimal tax;
    private BigDecimal totalPrice;
    
    /**
     * Constructor
     * @param primaryStage The primary stage
     */
    public BookingView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        createBookingScene();
    }
    
    /**
     * Create the booking scene
     */
    private void createBookingScene() {
        BorderPane root = new BorderPane();
        
        // Create the header with back button
        HBox header = new HBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #3498db;");
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            if (onBackButtonClicked != null) {
                onBackButtonClicked.run();
            }
        });
        
        Text title = new Text("Book Your Stay");
        title.setFill(Color.WHITE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        header.getChildren().addAll(backButton, title);
        
        root.setTop(header);
        
        // Create booking form content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        
        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        
        // Booking summary section
        VBox summaryBox = new VBox(10);
        summaryBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text summaryTitle = new Text("Booking Summary");
        summaryTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        GridPane summaryGrid = new GridPane();
        summaryGrid.setHgap(10);
        summaryGrid.setVgap(10);
        
        Text accommodationLabel = new Text("Accommodation:");
        accommodationText = new Text("Accommodation Name");
        Text datesLabel = new Text("Dates:");
        datesText = new Text("Check-in - Check-out (0 nights)");
        Text guestsLabel = new Text("Guests:");
        guestsText = new Text("2 Adults");
        
        summaryGrid.add(accommodationLabel, 0, 0);
        summaryGrid.add(accommodationText, 1, 0);
        summaryGrid.add(datesLabel, 0, 1);
        summaryGrid.add(datesText, 1, 1);
        summaryGrid.add(guestsLabel, 0, 2);
        summaryGrid.add(guestsText, 1, 2);
        
        summaryBox.getChildren().addAll(summaryTitle, summaryGrid);
        
        // Guest information form
        VBox guestInfoBox = new VBox(10);
        guestInfoBox.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text guestInfoTitle = new Text("Guest Information");
        guestInfoTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        GridPane guestInfoGrid = new GridPane();
        guestInfoGrid.setHgap(10);
        guestInfoGrid.setVgap(10);
        
        firstNameField = new TextField();
        lastNameField = new TextField();
        emailField = new TextField();
        phoneField = new TextField();
        
        guestInfoGrid.add(new Label("First Name:"), 0, 0);
        guestInfoGrid.add(firstNameField, 1, 0);
        guestInfoGrid.add(new Label("Last Name:"), 0, 1);
        guestInfoGrid.add(lastNameField, 1, 1);
        guestInfoGrid.add(new Label("Email:"), 0, 2);
        guestInfoGrid.add(emailField, 1, 2);
        guestInfoGrid.add(new Label("Phone:"), 0, 3);
        guestInfoGrid.add(phoneField, 1, 3);
        
        // Special requests
        Label specialRequestsLabel = new Label("Special Requests (optional):");
        specialRequestsArea = new TextArea();
        specialRequestsArea.setPrefRowCount(3);
        
        guestInfoBox.getChildren().addAll(guestInfoTitle, guestInfoGrid, specialRequestsLabel, specialRequestsArea);
        
        // Payment information
        VBox paymentBox = new VBox(10);
        paymentBox.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 5;");
        
        Text paymentTitle = new Text("Payment Information");
        paymentTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        
        // Payment method selection
        Label paymentMethodLabel = new Label("Payment Method:");
        paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.getItems().addAll("Credit Card", "PayPal", "Bank Transfer");
        paymentMethodCombo.setValue("Credit Card");
        
        // Credit card information form
        creditCardForm = new VBox(10);
        
        TextField cardNumberField = new TextField();
        cardNumberField.setPromptText("Card Number");
        
        HBox cardDetails = new HBox(10);
        TextField expiryField = new TextField();
        expiryField.setPromptText("MM/YY");
        expiryField.setPrefWidth(100);
        
        TextField cvvField = new TextField();
        cvvField.setPromptText("CVV");
        cvvField.setPrefWidth(70);
        
        TextField nameOnCardField = new TextField();
        nameOnCardField.setPromptText("Name on Card");
        
        cardDetails.getChildren().addAll(expiryField, cvvField);
        
        creditCardForm.getChildren().addAll(
                new Label("Card Number:"), cardNumberField,
                new Label("Expiry Date / CVV:"), cardDetails,
                new Label("Name on Card:"), nameOnCardField
        );
        
        // Show/hide credit card form based on payment method
        paymentMethodCombo.setOnAction(e -> {
            creditCardForm.setVisible(paymentMethodCombo.getValue().equals("Credit Card"));
            creditCardForm.setManaged(paymentMethodCombo.getValue().equals("Credit Card"));
        });
        
        // Price breakdown
        VBox priceBox = new VBox(5);
        priceBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 10; -fx-background-radius: 5;");
        
        Text priceBreakdownTitle = new Text("Price Breakdown");
        priceBreakdownTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        GridPane priceGrid = new GridPane();
        priceGrid.setHgap(10);
        priceGrid.setVgap(5);
        
        Text roomPriceLabel = new Text("$0.00 x 0 nights:");
        Text roomPriceValue = new Text("$0.00");
        Text taxLabel = new Text("Taxes and fees (12%):");
        Text taxValue = new Text("$0.00");
        Text totalLabel = new Text("Total:");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        totalPriceText = new Text("$0.00");
        totalPriceText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        priceGrid.add(roomPriceLabel, 0, 0);
        priceGrid.add(roomPriceValue, 1, 0);
        priceGrid.add(taxLabel, 0, 1);
        priceGrid.add(taxValue, 1, 1);
        priceGrid.add(new Separator(), 0, 2, 2, 1);
        priceGrid.add(totalLabel, 0, 3);
        priceGrid.add(totalPriceText, 1, 3);
        
        priceBox.getChildren().addAll(priceBreakdownTitle, priceGrid);
        
        // Terms and conditions
        termsCheckBox = new CheckBox("I agree to the terms and conditions");
        
        // Complete booking button
        Button completeBookingButton = new Button("Complete Booking");
        completeBookingButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white;");
        completeBookingButton.setPrefWidth(200);
        completeBookingButton.setOnAction(e -> {
            if (validateForm()) {
                if (onCompleteBookingClicked != null) {
                    BookingDetails details = new BookingDetails(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        phoneField.getText(),
                        specialRequestsArea.getText(),
                        paymentMethodCombo.getValue(),
                        termsCheckBox.isSelected(),
                        totalPrice
                    );
                    onCompleteBookingClicked.accept(details);
                }
            }
        });
        
        paymentBox.getChildren().addAll(
                paymentTitle, 
                paymentMethodLabel, 
                paymentMethodCombo, 
                creditCardForm, 
                priceBox,
                termsCheckBox, 
                completeBookingButton
        );
        
        // Add all sections to content
        content.getChildren().addAll(summaryBox, guestInfoBox, paymentBox);
        
        scrollPane.setContent(content);
        
        root.setCenter(scrollPane);
        
        bookingScene = new Scene(root, 800, 600);
    }
    
    /**
     * Show the booking view
     */
    public void show() {
        primaryStage.setScene(bookingScene);
        primaryStage.setTitle("Travel Finder - Book Your Stay");
    }
    
    /**
     * Setup the booking form with accommodation details
     * @param accommodationName The name of the accommodation
     * @param checkInDate The check-in date
     * @param checkOutDate The check-out date
     * @param guests The number of guests
     * @param nights The number of nights
     * @param basePrice The base price per night
     * @param totalBeforeTax The total price before tax
     * @param tax The tax amount
     * @param totalPrice The total price including tax
     */
    public void setupBookingForm(String accommodationName, LocalDate checkInDate, LocalDate checkOutDate, 
                               String guests, long nights, BigDecimal basePrice, BigDecimal totalBeforeTax, 
                               BigDecimal tax, BigDecimal totalPrice) {
        // Store booking information
        this.accommodationName = accommodationName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guests = guests;
        this.nights = nights;
        this.basePrice = basePrice;
        this.totalBeforeTax = totalBeforeTax;
        this.tax = tax;
        this.totalPrice = totalPrice;
        
        // Update UI with booking information
        accommodationText.setText(accommodationName);
        datesText.setText(checkInDate.toString() + " - " + checkOutDate.toString() + " (" + nights + " nights)");
        guestsText.setText(guests);
        
        // Update price breakdown
        VBox paymentBox = (VBox) ((VBox) ((ScrollPane) ((BorderPane) bookingScene.getRoot()).getCenter()).getContent()).getChildren().get(2);
        VBox priceBox = (VBox) paymentBox.getChildren().get(4);
        GridPane priceGrid = (GridPane) priceBox.getChildren().get(1);
        
        Text roomPriceLabel = (Text) priceGrid.getChildren().get(0);
        roomPriceLabel.setText(basePrice + " x " + nights + " nights:");
        
        Text roomPriceValue = (Text) priceGrid.getChildren().get(1);
        roomPriceValue.setText("$" + String.format("%.2f", totalBeforeTax));
        
        Text taxValue = (Text) priceGrid.getChildren().get(3);
        taxValue.setText("$" + String.format("%.2f", tax));
        
        totalPriceText.setText("$" + String.format("%.2f", totalPrice));
    }
    
    /**
     * Prefill user information
     * @param firstName The user's first name
     * @param lastName The user's last name
     * @param email The user's email
     * @param phone The user's phone number
     */
    public void prefillUserInfo(String firstName, String lastName, String email, String phone) {
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        emailField.setText(email);
        phoneField.setText(phone);
    }
    
    /**
     * Validate the booking form
     * @return true if the form is valid, false otherwise
     */
    private boolean validateForm() {
        if (firstNameField.getText().trim().isEmpty()) {
            showAlert("Please enter your first name");
            return false;
        }
        
        if (lastNameField.getText().trim().isEmpty()) {
            showAlert("Please enter your last name");
            return false;
        }
        
        if (emailField.getText().trim().isEmpty()) {
            showAlert("Please enter your email address");
            return false;
        }
        
        if (paymentMethodCombo.getValue().equals("Credit Card")) {
            TextField cardNumberField = (TextField) ((VBox) creditCardForm.getChildren().get(1)).getChildrenUnmodifiable().get(0);
            if (cardNumberField.getText().trim().isEmpty()) {
                showAlert("Please enter your card number");
                return false;
            }
            
            HBox cardDetails = (HBox) ((VBox) creditCardForm.getChildren().get(3)).getChildrenUnmodifiable().get(0);
            TextField expiryField = (TextField) cardDetails.getChildren().get(0);
            if (expiryField.getText().trim().isEmpty()) {
                showAlert("Please enter your card expiry date");
                return false;
            }
            
            TextField cvvField = (TextField) cardDetails.getChildren().get(1);
            if (cvvField.getText().trim().isEmpty()) {
                showAlert("Please enter your card CVV");
                return false;
            }
            
            TextField nameOnCardField = (TextField) ((VBox) creditCardForm.getChildren().get(5)).getChildrenUnmodifiable().get(0);
            if (nameOnCardField.getText().trim().isEmpty()) {
                showAlert("Please enter the name on your card");
                return false;
            }
        }
        
        if (!termsCheckBox.isSelected()) {
            showAlert("Please agree to the terms and conditions");
            return false;
        }
        
        return true;
    }
    
    /**
     * Show a booking confirmation
     * @param referenceNumber The booking reference number
     */
    public void showBookingConfirmation(String referenceNumber) {
        AlertUtil.showBookingConfirmation(referenceNumber);
    }
    
    /**
     * Show an alert
     * @param message The alert message
     */
    public void showAlert(String message) {
        AlertUtil.showAlert(Alert.AlertType.WARNING, "Booking Error", message);
    }
    
    // Setter methods for event handlers
    
    public void setOnBackButtonClicked(Runnable handler) {
        this.onBackButtonClicked = handler;
    }
    
    public void setOnCompleteBookingClicked(Consumer<BookingDetails> handler) {
        this.onCompleteBookingClicked = handler;
    }
    
    /**
     * Class to hold booking details
     */
    public class BookingDetails {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String specialRequests;
        private String paymentMethod;
        private boolean termsAccepted;
        private BigDecimal totalPrice;
        
        public BookingDetails(String firstName, String lastName, String email, String phone,
                            String specialRequests, String paymentMethod, boolean termsAccepted,
                            BigDecimal totalPrice) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.phone = phone;
            this.specialRequests = specialRequests;
            this.paymentMethod = paymentMethod;
            this.termsAccepted = termsAccepted;
            this.totalPrice = totalPrice;
        }
        
        public String getFirstName() {
            return firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public String getEmail() {
            return email;
        }
        
        public String getPhone() {
            return phone;
        }
        
        public String getSpecialRequests() {
            return specialRequests;
        }
        
        public String getPaymentMethod() {
            return paymentMethod;
        }
        
        public boolean isTermsAccepted() {
            return termsAccepted;
        }
        
        public BigDecimal getTotalPrice() {
            return totalPrice;
        }
    }
}