package ecse321.t08.rideshare.controller;

import ecse321.t08.rideshare.entity.ATrip;
import ecse321.t08.rideshare.repository.ATripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/trip")
public class ATripController {
    @Autowired
    ATripRepository repository;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public String createTrip(
        @RequestParam("status") Integer status,
        @RequestParam("cost") String cost,
        @RequestParam("startDate") Integer startDate,
        @RequestParam("endDate") Integer endDate,
        @RequestParam("startLocation") String startLocation,
        @RequestParam("stops") String stops,
        @RequestParam("vehicleid") Integer vehicleId,
        @RequestParam("driveruser") String driverUsername,
        @RequestParam("driverpass") String driverPassword
    ) {
        ATrip result = repository.createATrip(
            status, 
            cost, 
            startDate, 
            endDate, 
            startLocation, 
            stops, 
            vehicleId, 
            driverUsername, 
            driverPassword
        );
        if (result == null) {
            return "Unable to create trip.";
        } else {
            return "Trip created starting at " + startLocation + "!";
        }
    }

    // Get list of trips if you are admin
    @RequestMapping(value = "/utripslist", method = RequestMethod.POST)
    public List getUnfilteredTripsList(
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.getUnfilteredTripsList(username, password);
    }

    @RequestMapping(value = "/trips/{id}", method = RequestMethod.GET)
    public ATrip getTrip(@PathVariable("id") int id) {
        return repository.getTrip(id);
    }

    // User selects trip and we record it on ATrip
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public String selectTrip(
        @RequestParam("tripid") int ATripID,
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.selectTrip(ATripID, username, password);
    }

    // Cancel trip based on ID, if you are a user
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public String cancelATrip(
        @RequestParam("tripid") int ATripID,
        @RequestParam("username") String username,
        @RequestParam("password") String password
    ) {
        return repository.cancelATrip(ATripID, username, password);
    }


    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public String changeTripStatus(
        @RequestParam("tripid") Integer ATripID,
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        @RequestParam("tripstatus") Integer status
    ) {
        return repository.changeTripStatus(ATripID, username, password, status); // 0 for ongoing, 1 for planned, 2 for completed
    }

    @RequestMapping(value = "/passengers", method = RequestMethod.POST)
    public List<String> passengerOnTrip(@RequestParam("tripid") Integer ATripID) {
        return repository.findPassengerOnTrip(ATripID);
    }

    @RequestMapping(value = "/driver", method = RequestMethod.POST)
    public int driverOnTrip(@RequestParam("tripid") Integer ATripID) {
        return repository.findDriverOnTrip(ATripID);
    }

    @RequestMapping(value = "/usertrips", method = RequestMethod.POST)
    public List<Integer> usertrip(@RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        return repository.userTrip(username, password);
    }

    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public List<Integer> findTrip(
        @RequestParam(value = "startloc", required = false) String startLocation,
        @RequestParam(value = "stop", required = false) String stop,
        @RequestParam(value = "startdate", required = false, defaultValue = "-1") Integer startdate,
        @RequestParam(value = "enddate", required = false, defaultValue = "-1") Integer enddate,
        @RequestParam(value = "vehtype", required = false) String vehtype,
        @RequestParam(value = "maxcost", required = false, defaultValue = "-1.0") Double maxcost
    ) {
        if (startLocation == null) {
            startLocation = "";
        }
        if (stop == null) {
            stop = "";
        }
        if (startdate == null) {
            startdate = -1;
        }
        if (enddate == null) {
            enddate = -1;
        }
        if (vehtype == null) {
            vehtype = "";
        }
        if (maxcost == null) {
            maxcost = -1.0;
        }
        return repository.findtrip(startLocation, stop, startdate, enddate, vehtype, maxcost);
    }
}