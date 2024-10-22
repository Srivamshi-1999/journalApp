package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal") // same journal path is used for get and post calls
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;
    @GetMapping
    public List<JournalEntry> getAll(){ // fecthes all journals in list of arrays
        return  journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry creatEntry(@RequestBody JournalEntry myEntry){ // puts data in maps
        myEntry.setDate(LocalDate.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry  getJournalEntryById(@PathVariable ObjectId myId){

        return  journalEntryService.findbyId(myId).orElse(null);
    }
    @DeleteMapping("/id/{myId}")
    public boolean deleteJournalEntrybyID(@PathVariable ObjectId myId){

         journalEntryService.deleteById(myId);
         return true;
    }
    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry old = new JournalEntryService().findbyId(myId).orElse(null);
        if(old != null){
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());

        }
        journalEntryService.saveEntry(old);
       return old;
    }
}
