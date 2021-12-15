package adeo.leroymerlin.cdp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAllBy();
    }

    public void delete(Long id) {
        eventRepository.delete(id);
    }

    public void updateStars(Event event){
        Event evt = eventRepository.findOne(event.getId());
        evt.setNbStars(event.getNbStars());
        eventRepository.save(evt);
    }

    public List<Event> getFilteredEvents(String query) {
        List<Event> events = eventRepository.findAllBy();
        // Filter the events list in pure JAVA here
        List<Event> searchEvents = new ArrayList<>();
        events.forEach(event -> {
            event.getBands().forEach(band ->{
                band.getMembers().forEach(member -> {
                    if(member.getName().contains(query)){
                        searchEvents.add(event);
                    }
                });
            });
        });

        return searchEvents;
    }
}
