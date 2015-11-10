package org.smurve.hsr2014.ctrl;

import org.smurve.hsr2014.domain.gents.GentsMetaData;
import org.smurve.hsr2014.domain.gents.Labelled;
import org.smurve.hsr2014.repo.gents.GentDataRepository;
import org.smurve.hsr2014.repo.gents.MetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GentsMetaDataController {

  @Autowired
  private MetaDataRepository repo;

  @Autowired
  private GentDataRepository dataRepo;

  @RequestMapping(value = "/gents/metadata")
    @ResponseBody
  public GentsMetaData retrieveMetaData() {
    return repo.findOne("1");
    }


  @RequestMapping(value = "/gents/data/{gent}")
  @ResponseBody
  @SuppressWarnings("unchecked")
  public List<Labelled> findDall(@PathVariable String gent) {

    return dataRepo.findAll(gent);
  }
}
