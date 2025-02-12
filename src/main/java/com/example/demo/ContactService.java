package com.example.demo.service;
import com.example.demo.model.Contact;
import com.example.demo.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ContactService {
    @Autowired
    private ContactRepository contactRepository;

    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }
     public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
    public Contact updateContact(Long id, Contact updatedContact) {
    return contactRepository.findById(id).map(contact -> {
        contact.setFirstName(updatedContact.getFirstName());
        contact.setLastName(updatedContact.getLastName());
        contact.setEmail(updatedContact.getEmail());
        contact.setPhoneNumber(updatedContact.getPhoneNumber());
        contact.setGroupType(updatedContact.getGroupType());
        contact.setPhysicalAddress(updatedContact.getPhysicalAddress());

        // ✅ Update image only if a new one is provided
        if (updatedContact.getContactImage() != null && !updatedContact.getContactImage().isEmpty()) {
            contact.setContactImage(updatedContact.getContactImage());
        }

        return contactRepository.save(contact);
    }).orElseThrow(() -> new RuntimeException("Contact not found with id: " + id));
}

}
