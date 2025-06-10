package org.fossify.messages.helpers

/**
 * Configuration file for message filters and keywords
 * This file can be easily modified to add new filters and keywords
 */
object MessageFilters {
    
    /**
     * Keyword-based filters
     * Each filter contains a list of keywords to search for in messages
     */
    object Keywords {
        // Discount/Sale related keywords
        val DISCOUNT_KEYWORDS = listOf(
            "تخفیف",      // Discount in Persian/Farsi
            "discount",   // English
            "sale",       // English
            "offer",      // English
            "تخفیفات",    // Discounts (plural) in Persian/Farsi
            "فروش",       // Sale in Persian/Farsi
            "پیشنهاد"     // Offer in Persian/Farsi
        )
        
        // ISP/Telecom company names
        val ISP_SENDERS = listOf(
            "HAMRAH AVAL",
            "RIGHTEL",
            "RIGHTEL+", 
            "RighTel",
            "IRANCELL",
            "irancell",
            "Irancell",
            "Aptel",
            "HamraheMan",
            "SamanTel",
            "HAMRAHAVAL"
        )
        
        // OTP/Verification code related keywords
        val OTP_KEYWORDS = listOf(
            "code:",        // English
            ":کد تایید",    // Persian for ": verification code"
            "رمز:",         // Persian for "password:"
            "verification code",
            "کد تایید",     // Persian for "verification code"
            "رمز یکبار مصرف", // Persian for "one-time password"
            "OTP",
            "PIN"
        )
        
        // Banking related keywords and sender names
        val BANKING_KEYWORDS = listOf(
            "Bank Mellat",
            "Bank Melli", 
            "بانک ملت",     // Bank Mellat in Persian
            "بانک ملی",     // Bank Melli in Persian
            "Parsian Bank",
            "بانک پارسیان",  // Parsian Bank in Persian
            "Bank Tejarat",
            "بانک تجارت",   // Tejarat Bank in Persian
            "Bank Saderat",
            "بانک صادرات",  // Saderat Bank in Persian
            "Bank Pasargad",
            "بانک پاسارگاد", // Pasargad Bank in Persian
            "Saman Bank",
            "بانک سامان",   // Saman Bank in Persian
            "Eghtesad Novin",
            "اقتصاد نوین",   // Eghtesad Novin in Persian
        )
        
        // Banking phone numbers
        val BANKING_PHONE_NUMBERS = listOf(
            "09999987641"
            // Add more specific banking phone numbers here
        )
        
        // You can add more keyword categories here
        // Example:
        // val BANKING_KEYWORDS = listOf("bank", "بانک", "transaction", "تراکنش")
    }
    
    /**
     * Sender-based filters
     * You can add specific phone numbers or sender patterns here
     */
    object Senders {
        // Example: Banking numbers, service numbers, etc.
        val PROMOTIONAL_SENDERS = listOf<String>(
            // Add specific numbers here, e.g.:
            // "1000", "2000", etc.
        )
        
        // Short codes (typically 4-6 digits)
        val SHORT_CODE_PATTERN = Regex("^\\d{4,6}$")
    }
    
    /**
     * Helper function to check if a message contains discount keywords
     */
    fun containsDiscountKeywords(messageBody: String): Boolean {
        val lowercaseBody = messageBody.lowercase()
        return Keywords.DISCOUNT_KEYWORDS.any { keyword ->
            lowercaseBody.contains(keyword.lowercase())
        }
    }
    
    /**
     * Helper function to check if a sender is promotional
     */
    fun isPromotionalSender(phoneNumber: String): Boolean {
        return Senders.PROMOTIONAL_SENDERS.contains(phoneNumber) ||
                Senders.SHORT_CODE_PATTERN.matches(phoneNumber)
    }
    
    /**
     * Helper function to check if a message is from an ISP/telecom company
     */
    fun isFromISP(senderName: String): Boolean {
        return Keywords.ISP_SENDERS.any { ispName ->
            senderName.equals(ispName, ignoreCase = true)
        }
    }
    
    /**
     * Helper function to check if a message contains OTP/verification code keywords
     */
    fun containsOtpKeywords(messageBody: String): Boolean {
        val lowercaseBody = messageBody.lowercase()
        return Keywords.OTP_KEYWORDS.any { keyword ->
            lowercaseBody.contains(keyword.lowercase())
        }
    }
    
    /**
     * Helper function to check if a message is from a bank
     */
    fun isFromBank(senderName: String, phoneNumber: String = "", messageBody: String = ""): Boolean {
        // Check if sender name matches banking keywords
        val lowercaseSender = senderName.lowercase()
        val bankingSenderMatch = Keywords.BANKING_KEYWORDS.any { keyword ->
            lowercaseSender.contains(keyword.lowercase())
        }
        
        // Check if phone number is a known banking number
        val bankingPhoneMatch = Keywords.BANKING_PHONE_NUMBERS.contains(phoneNumber)
        
        // Check if message content contains banking keywords (for additional detection)
        val lowercaseBody = messageBody.lowercase()
        val bankingContentMatch = Keywords.BANKING_KEYWORDS.any { keyword ->
            lowercaseBody.contains(keyword.lowercase())
        }
        
        return bankingSenderMatch || bankingPhoneMatch || bankingContentMatch
    }
    
    // Add more helper functions as needed
}
