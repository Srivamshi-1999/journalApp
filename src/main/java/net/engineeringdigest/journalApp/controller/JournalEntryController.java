package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal") // same journal path is used for get and post calls
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){ // fecthes all journals in list of arrays
        return  new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean creatEntry(@RequestBody JournalEntry journalEntry){ // puts data in maps
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @GetMapping("/id/{myId}")
    public JournalEntry  getJournalEntryById(@PathVariable Long myId){
       return  journalEntries.get(myId);
    }
    @DeleteMapping("/id/{myId}")
    public boolean deleteJournalEntrybyID(@PathVariable Long myId){
        journalEntries.remove(myId);
        return true;
    }
    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable Long myId, @RequestBody JournalEntry myEntry){
        return journalEntries.put(myId,myEntry);
    }
}
