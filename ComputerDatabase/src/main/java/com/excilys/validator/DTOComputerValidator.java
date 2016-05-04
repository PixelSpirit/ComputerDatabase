package com.excilys.validator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.excilys.dto.DTOComputer;

public class DTOComputerValidator {

    private static void isIdValid(String id) {
        Pattern intPattern = Pattern.compile("^\\d+$");
        if (id == null || !intPattern.matcher(id).matches() || Long.parseLong(id) <= 0) {
            throw new ValidatorException();
        }
    }

    private static void isCompanyIdValid(String id) {
        Pattern intPattern = Pattern.compile("^\\d+$");
        if (id != null) {
            if (!id.equals("")) {
                if (!intPattern.matcher(id).matches() || Long.parseLong(id) < 0) {
                    throw new ValidatorException();
                }
            }
        } else {
            throw new ValidatorException();
        }

    }

    private static boolean isNameValid(String name) {
        if (name != null) {
            return !name.equals("");
        } else {
            throw new ValidatorException();
        }
    }

    private static boolean isDateValide(String introduced) {
        Pattern datePattern = Pattern.compile("^[0-9]{4}-(0[1-9]|1[012])-([0-2][0-9]|3[0-1])$");
        if (introduced != null) {
            if (introduced.equals("")) {
                return true;
            }
            if (datePattern.matcher(introduced).matches()) {
                LocalDate date = LocalDate.parse(introduced);
                return (date.isAfter(LocalDate.of(1970, 1, 1)) && date.isBefore(LocalDate.of(2030, 1, 1)));
            }
            return false;
        } else {
            throw new ValidatorException();
        }
    }

    private static boolean isDateOrderValid(String introduced, String discontinued) {
        if (!introduced.equals("") && !discontinued.equals("")) {
            LocalDate i = LocalDate.parse(introduced);
            LocalDate d = LocalDate.parse(discontinued);
            return i.isBefore(d);
        }
        return true;
    }

    public static List<String> isValideDTOComputer(DTOComputer dto) {
        ArrayList<String> l = new ArrayList<String>();
        isCompanyIdValid(dto.getCompanyId());
        if (!isNameValid(dto.getName())) {
            l.add("name");
        }
        if (!isNameValid(dto.getCompanyName())) {
            l.add("companyName");
        }
        boolean introduced = isDateValide(dto.getIntroduced());
        boolean discontinued = isDateValide(dto.getDiscontinued());
        if (!introduced) {
            l.add("introduced");
        }
        if (!discontinued) {
            l.add("discontinued");
        }
        if (introduced && discontinued) {
            if (!isDateOrderValid(dto.getIntroduced(), dto.getDiscontinued())) {
                l.add("order");
            }
        }
        return l;
    }

}
