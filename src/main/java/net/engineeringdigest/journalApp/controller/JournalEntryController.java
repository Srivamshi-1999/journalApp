package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/journal") // same journal path is used for get and post calls
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){ // fecthes all journals in list of arrays and ? say wildcard anything can be sent
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if(all != null){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> creatEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){ // puts data in maps\
        try{
            journalEntryService.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?>  getJournalEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.findbyId(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntrybyID(@PathVariable ObjectId myId, @PathVariable String userName){

         journalEntryService.deleteById(myId, userName);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId,
                                                    @RequestBody JournalEntry newEntry,
                                                    @PathVariable String userName){
        JournalEntry old = journalEntryService.findbyId(myId).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && ! newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(newEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
    }
}
