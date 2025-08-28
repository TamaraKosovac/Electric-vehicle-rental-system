package org.unibl.etf.ip.erent.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.erent.dto.PromotionDTO;
import org.unibl.etf.ip.erent.model.Promotion;
import org.unibl.etf.ip.erent.service.PromotionService;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
@RequiredArgsConstructor
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public List<Promotion> getAll() {
        return promotionService.findAll();
    }

    @GetMapping("/{id}")
    public Promotion getById(@PathVariable Long id) {
        return promotionService.findById(id);
    }

    @PostMapping
    public Promotion create(@RequestBody PromotionDTO dto) {
        return promotionService.createFromDTO(dto);
    }

    @PutMapping("/{id}")
    public Promotion update(@PathVariable Long id, @RequestBody PromotionDTO dto) {
        return promotionService.updateFromDTO(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        promotionService.delete(id);
    }

    @GetMapping(value = "/rss", produces = "application/xml")
    public String getRssFeed() {
        List<Promotion> promotions = promotionService.findAll();

        StringBuilder rss = new StringBuilder();
        rss.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        rss.append("<rss version=\"2.0\"><channel>");
        rss.append("<title>ETFBL_IP Promotions</title>");
        rss.append("<link>http://localhost:8080/api/promotions/rss</link>");
        rss.append("<description>Latest promotions and announcements</description>");

        for (Promotion p : promotions) {
            rss.append("<item>");
            rss.append("<title>").append(p.getTitle()).append("</title>");
            rss.append("<description>").append(p.getContent()).append("</description>");
            rss.append("<pubDate>").append(p.getCreatedAt()).append("</pubDate>");
            rss.append("<startDate>").append(p.getStartDate()).append("</startDate>");
            rss.append("<endDate>").append(p.getEndDate()).append("</endDate>");
            rss.append("</item>");
        }

        rss.append("</channel></rss>");
        return rss.toString();
    }

}
